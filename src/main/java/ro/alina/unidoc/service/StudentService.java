package ro.alina.unidoc.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ro.alina.unidoc.entity.*;
import ro.alina.unidoc.model.*;
import ro.alina.unidoc.model.filters.StudentFilter;
import ro.alina.unidoc.model.type.DocumentStatusType;
import ro.alina.unidoc.model.type.DocumentType;
import ro.alina.unidoc.repository.*;
import ro.alina.unidoc.utils.GenericSpecification;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentService {

    private static final String UPLOAD_FOLDER = "C:\\Users\\User\\Desktop\\persistence\\student\\documents\\secretary";
    private static final String UPLOAD_FOLDER_FOR_OWN_DOCUMENTS = "C:\\Users\\User\\Desktop\\persistence\\student\\documents\\own";
    private final GenericSpecification<Student> studentGenericSpecification;
    private final StudentRepository studentRepository;
    private final PhoneNumberRepository phoneNumberRepository;
    private final SecretaryDocumentRepository secretaryDocumentRepository;
    private final StudentDocumentRepository studentDocumentRepository;
    private final SecretaryService secretaryService;
    private final GenericSpecification<StudentDocument> studentDocumentGenericSpecification;
    private final GenericSpecification<Notification> notificationGenericSpecification;
    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;
    private final SecretaryResponseDocumentRepository secretaryResponseDocumentRepository;
    private final PasswordGeneratorService passwordGeneratorService;
    private final UserRepository userRepository;
    private final StudyGroupRepository studyGroupRepository;
    private final SecretaryAllocationRepository secretaryAllocationRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileEncryptionDecryptionService fileEncryptionDecryptionService;

    public Page<StudentModel> getAllStudents(final int pageSize, final int pageNumber) {
        //final var specification = getSpecifications(filter);
        var pageReq = PageRequest.of(pageNumber, pageSize);
        return studentRepository.findAll(pageReq).map(this::toStudentModel);
    }

    private Specification<Student> getSpecifications(final StudentFilter filter) {
        return Objects.requireNonNull(studentGenericSpecification.where(
                studentGenericSpecification.isPropertyLike("first_name", filter.getFirstName()))
                .and(studentGenericSpecification.isPropertyLike("last_name", filter.getLastName()))
                .and(studentGenericSpecification.isNestedPropertyLike("user.email", filter.getEmailAddress()))
                .and(studentGenericSpecification.isPropertyEqualNumber("secretary_allocation_id", filter.getSecretaryAllocationId()))
                .and(studentGenericSpecification.isPropertyEqualNumber("learning_type_id", filter.getLearningTypeId()))
                .and(studentGenericSpecification.isPropertyEqualNumber("university_study_type_id", filter.getUniversityStudyId()))
                .and(studentGenericSpecification.isPropertyEqualNumber("domain_id", filter.getDomainId()))
                .and(studentGenericSpecification.isPropertyEqualNumber("study_program_id", filter.getStudyProgramId()))
                .and(studentGenericSpecification.isPropertyEqualNumber("study_year_id", filter.getStudyYearId()))
                .and(studentGenericSpecification.isPropertyEqualNumber("study_group_id", filter.getStudyGroupId())));
    }

    private StudentModel toStudentModel(final Student student) {
        return StudentModel.builder()
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .emailAddress(student.getUser().getEmail())
                .study(student.getLearningType().getName().toString() + ", " + student.getUniversityStudyType().getName().toString()
                        + ", " + student.getDomain().getName().toString() + ", " + student.getStudyProgram().getName() + ", "
                        + student.getStudyYear().getName() + ", " + student.getStudyGroup().getName())
                .cnp(student.getCnp())
                .registrationNumber(student.getRegistrationNumber())
                .phoneNumbers(phoneNumberRepository.findAllByUser(student.getUser())
                        .stream()
                        .map(PhoneNumber::getPhoneNumber)
                        .collect(Collectors.toList()))
                .build();
    }

    public List<RequiredDocumentsRowModel> getRequiredSecretaryDocuments(final Long studentId) {
        var student = studentRepository.getOne(studentId);
        return secretaryDocumentRepository.findAllBySecretaryAllocation_Id(student.getSecretaryAllocation().getId()).stream()
                .map(secretaryDocument -> toRequiredDocumentsRowModel(secretaryDocument, studentId))
                .collect(Collectors.toList());
    }

    private RequiredDocumentsRowModel toRequiredDocumentsRowModel(final SecretaryDocument secretaryDocument, final Long studentId) {
        var model = new RequiredDocumentsRowModel();
        model.setSecretaryDocumentId(secretaryDocument.getId());
        model.setSecretaryDocumentName(secretaryDocument.getName());
        model.setSecretaryDocumentPathName(secretaryDocument.getFilePathName());
        model.setEndDateOfUpload(secretaryDocument.getEndDateOfUpload());
        studentDocumentRepository.findByStudentIdAndSecretaryDocumentId(studentId, secretaryDocument.getId())
                .ifPresent(studentDocument -> model.setStudentDocumentModel(StudentDocumentModel.builder()
                        .name(studentDocument.getName())
                        .filePathName(studentDocument.getFilePathName())
                        .dateOfUpload(studentDocument.getDateOfUpload())
                        .status(secretaryService.getStatusToANicerForm(studentDocument.getStatus()))
                        .build()));
        return model;
    }

    public Response uploadSecretaryDocument(final MultipartFile file, final Long studentId, final Long secretaryDocumentId,
                                            final String name) {
        if (file.isEmpty()) {
            return Response.builder().type("ERROR").message("The file is empty!").build();
        }
        try {
            byte[] bytes = file.getBytes();
            var secretaryDocument = secretaryDocumentRepository.getOne(secretaryDocumentId);
            if (secretaryDocument.getEndDateOfUpload().isBefore(LocalDateTime.now())) {
                return Response.builder().type("ERROR").message("The limit date of the file has been exceeded!").build();
            }
            Path path = Paths.get(UPLOAD_FOLDER + "\\" + secretaryDocument.getSecretaryAllocation().getId() + "\\student\\"
                    + studentId + "\\" + file.getOriginalFilename());
            File directory = new File(UPLOAD_FOLDER + "\\" + secretaryDocument.getSecretaryAllocation().getId() + "\\student\\"
                    + studentId + "\\");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            Files.write(path, bytes);
            var student = studentRepository.getOne(studentId);
            fileEncryptionDecryptionService.encryptFile(path.toString(), student.getCnp());

            studentDocumentRepository.findByStudentIdAndSecretaryDocumentId(studentId, secretaryDocumentId).ifPresentOrElse(studentDocument -> {
                studentDocument.setFilePathName(path.toString());
                studentDocument.setName(name);
                studentDocument.setDateOfUpload(LocalDateTime.now());
                studentDocumentRepository.saveAndFlush(studentDocument);
            }, () -> studentDocumentRepository.save(StudentDocument.builder()
                    .name(name)
                    .student(studentRepository.getOne(studentId))
                    .secretaryDocument(secretaryDocument)
                    .filePathName(path.toString())
                    .dateOfUpload(LocalDateTime.now())
                    .documentType(DocumentType.SECRETARY)
                    .status(DocumentStatusType.IN_PROGRESS)
                    .build()));
        } catch (Exception e) {
            return Response.builder().type("ERROR").message("There has been an error").build();
        }
        return Response.builder().type("SUCCESS").message("The file was uploaded successfully!").build();
    }

    public Page<StudentDocumentModel> getOwnDocuments(final Long studentId, final int pageNumber, final int pageSize) {
        final var specification = getSpecification(studentId);
        var pageReq = PageRequest.of(pageNumber, pageSize, Sort.by("dateOfUpload").descending());
        return studentDocumentRepository.findAll(specification, pageReq)
                .map(this::toStudentDocumentModel);
    }

    private Specification<StudentDocument> getSpecification(final Long studentId) {
        return Objects.requireNonNull(studentDocumentGenericSpecification.where(
                studentDocumentGenericSpecification.isNestedPropertyEqualNumber("student.id", studentId))
                .and(studentDocumentGenericSpecification.isDocumentTypeEqual("documentType", "OWN")));
    }

    private StudentDocumentModel toStudentDocumentModel(final StudentDocument studentDocument) {
        String responseDocumentFilePath = null;
        if (secretaryResponseDocumentRepository.findByStudentDocument(studentDocument).isPresent()) {
            responseDocumentFilePath = secretaryResponseDocumentRepository.findByStudentDocument(studentDocument).get()
                    .getFilePathName();
        }
        return StudentDocumentModel.builder()
                .name(studentDocument.getName())
                .description(studentDocument.getDescription())
                .dateOfUpload(studentDocument.getDateOfUpload())
                .status(secretaryService.getStatusToANicerForm(studentDocument.getStatus()))
                .id(studentDocument.getId())
                .filePathName(studentDocument.getFilePathName())
                .responseDocumentPathName(responseDocumentFilePath)
                .build();
    }

    public Response uploadOwnDocument(final MultipartFile file, final Long studentId, final String description,
                                      final String name) {
        if (file.isEmpty()) {
            return Response.builder().type("ERROR").message("The file is empty!").build();
        }
        AtomicReference<String> type = new AtomicReference<>("SUCCESS");
        AtomicReference<String> message = new AtomicReference<>("The file was uploaded successfully! You will be notified whenever the status of it changes!");
        try {
            byte[] bytes = file.getBytes();

            Path path = Paths.get(UPLOAD_FOLDER_FOR_OWN_DOCUMENTS + "\\" + studentId + "\\" + file.getOriginalFilename());
            File directory = new File(UPLOAD_FOLDER_FOR_OWN_DOCUMENTS + "\\" + studentId + "\\");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            Files.write(path, bytes);

            var student = studentRepository.getOne(studentId);
            fileEncryptionDecryptionService.encryptFile(path.toString(), student.getCnp());

            studentDocumentRepository.findByStudentIdAndFilePathName(studentId, path.toString()).ifPresentOrElse(studentDocument -> {
                type.set("ERROR");
                message.set("The file already exists, it is called " + studentDocument.getName());
            }, () -> {
                notificationService.sendSimpleMessage(student.getSecretaryAllocation().getSecretary().getUser().getEmail(), "New document added by a student", "A new document called " + name + " has been uploaded by the student "
                        + student.getLastName() + " " + student.getFirstName() + " from the group " + student.getStudyGroup().getName());
                var studentDocument = studentDocumentRepository.save(StudentDocument.builder()
                        .name(name)
                        .student(studentRepository.getOne(studentId))
                        .description(description)
                        .filePathName(path.toString())
                        .dateOfUpload(LocalDateTime.now())
                        .documentType(DocumentType.OWN)
                        .status(DocumentStatusType.IN_PROGRESS)
                        .build());
                notificationRepository.save(Notification.builder()
                        .type("SECRETARY")
                        .seen(false)
                        .date(LocalDateTime.now())
                        .message("New document has been uploaded by a student!")
                        .studentDocument(studentDocument)
                        .build());
            });
        } catch (Exception e) {
            return Response.builder().type("ERROR").message("There has been an error").build();
        }
        return Response.builder().type(type.get()).message(message.get()).build();
    }

    public Page<StudentNotificationModel> getStudentNotifications(final Long studentId, final int pageSize,
                                                                  final int pageNumber, final String columnName,
                                                                  final String direction) {
        final var specification = getNotificationSpecification(studentId);
        var sort = direction.equals("desc") ? Sort.by(columnName).descending() : Sort.by(columnName).ascending();
        var pageReq = PageRequest.of(pageNumber, pageSize, sort);
        return notificationRepository.findAll(specification, pageReq)
                .map(this::toStudentNotification);

    }

    private Specification<Notification> getNotificationSpecification(final Long studentId) {
        return Objects.requireNonNull(notificationGenericSpecification
                .where(notificationGenericSpecification.isNestedNestedPropertyEqualNumber("studentDocument.student.id", studentId))
                .and(notificationGenericSpecification.isPropertyEqual("type", "STUDENT")));
    }

    private StudentNotificationModel toStudentNotification(final Notification notification) {
        return StudentNotificationModel.builder()
                .id(notification.getId())
                .studentDocumentId(notification.getStudentDocument().getId())
                .documentName(notification.getStudentDocument().getName())
                .documentType(notification.getStudentDocument().getDocumentType().toString())
                .date(notification.getDate())
                .message(notification.getMessage())
                .seen(notification.getSeen())
                .build();
    }

    public Response markNotificationAsSeen(final Long notificationId) {
        try {
            notificationRepository.findById(notificationId).ifPresent(notification -> {
                notification.setSeen(true);
                notificationRepository.save(notification);
            });
            return Response.builder()
                    .type("SUCCESS")
                    .message("Notification marked as seen")
                    .build();
        } catch (Exception e) {
            return Response.builder()
                    .type("ERROR")
                    .message("Something happened when trying to")
                    .build();
        }
    }

    public int getUnseenNotifications(final Long userId) {
        return notificationRepository.countBySeenAndStudentDocument_Student_IdAndType(false, studentRepository.findByUserId(userId).getId(), "STUDENT");
    }

    public StudentModel getUserProfile(final Long userId) {
        var student = studentRepository.findByUserId(userId);
        return StudentModel.builder()
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .emailAddress(student.getUser().getEmail())
                .phoneNumbers(phoneNumberRepository.findAllByUser(student.getUser()).stream().map(PhoneNumber::getPhoneNumber).collect(Collectors.toList()))
                .cnp(student.getCnp())
                .registrationNumber(student.getRegistrationNumber())
                .secretaryName(student.getSecretaryAllocation().getSecretary().getFirstName() + " " + student.getSecretaryAllocation().getSecretary().getLastName())
                .secretaryPhoneNumbers(phoneNumberRepository.findAllByUser(student.getSecretaryAllocation().getSecretary().getUser())
                        .stream()
                        .map(PhoneNumber::getPhoneNumber)
                        .collect(Collectors.toList()))
                .secretaryEmailAddress(student.getSecretaryAllocation().getSecretary().getUser().getEmail())
                .build();
    }

    public Response deleteOwnDocument(final Long documentId) {
        try {
            notificationRepository.deleteByStudentDocument_Id(documentId);
            studentDocumentRepository.deleteById(documentId);
            return Response.builder()
                    .type("SUCCESS")
                    .message("Student document deleted successfully!")
                    .build();
        } catch (Exception e) {
            return Response.builder()
                    .type("ERROR")
                    .message("There has been an error when trying to delete the student document!")
                    .build();
        }
    }

    public Response uploadStudents(final MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            CSVParser csvParser = new CSVParser(bufferedReader, CSVFormat.DEFAULT);
            StreamSupport.stream(csvParser.spliterator(), false)
                    .forEach(record -> {
                        try {
                            var generatedPassword = passwordGeneratorService.generatePassayPassword();
                            User user = fromRecordToUserEntity(record, generatedPassword);
                            Optional<StudyGroup> studyGroup = studyGroupRepository.findByName(record.get(6));
                            if (userRepository.findByEmail(user.getEmail()).isEmpty() && studyGroup.isPresent()) {
                                Student student = fromRecordToStudentEntity(record, studyGroup.get());
                                if (studentRepository.findByCnp(student.getCnp()).isEmpty()) {
                                    PhoneNumber number = PhoneNumber.builder()
                                            .phoneNumber(record.get(5))
                                            .build();
                                    user = userRepository.save(user);
                                    student.setUser(user);
                                    studentRepository.save(student);
                                    number.setUser(user);
                                    phoneNumberRepository.save(number);
                                    notificationService.sendSimpleMessage(user.getEmail(), "Generated password for Unidoc", "Hello! Here is your generated password for the app Unidoc. Use it to change your password, not to login in! The password is " + generatedPassword);
                                }
                            }

                        } catch (Exception e) {
                            System.out.println("There was an error when trying to process and save this user!");
                        }
                    });
            return Response.builder().type("SUCCESS").message("Students were uploaded successfully!").build();
        } catch (IOException e) {
            return Response.builder().type("ERROR").message("There has been an error with the upload!").build();
        }
    }

    public User fromRecordToUserEntity(final CSVRecord csvRecord, final String generatedPassword) {
        return User.builder()
                .email(csvRecord.get(4))
                .password(passwordEncoder.encode(generatedPassword))
                .role(UserRole.STUDENT)
                .isActive(false)
                .build();
    }

    public Student fromRecordToStudentEntity(final CSVRecord csvRecord, final StudyGroup studyGroup) {
        var learningType = studyGroup.getStudyYear().getStudyProgram().getDomain().getUniversityStudyType().getLearningType();
        var universityStudyType = studyGroup.getStudyYear().getStudyProgram().getDomain().getUniversityStudyType();
        var domain = studyGroup.getStudyYear().getStudyProgram().getDomain();
        var studyProgram = studyGroup.getStudyYear().getStudyProgram();
        var studyYear = studyGroup.getStudyYear();
        Optional<SecretaryAllocation> allocation = secretaryAllocationRepository.findByLearningType_IdAndUniversityStudyType_IdAndDomain_IdAndStudyProgram_IdAndStudyYear_Id(learningType.getId(),
                universityStudyType.getId(), domain.getId(), studyProgram.getId(), studyYear.getId());
        return Student.builder()
                .lastName(csvRecord.get(0))
                .firstName(csvRecord.get(1))
                .cnp(csvRecord.get(2))
                .registrationNumber(csvRecord.get(3))
                .learningType(learningType)
                .universityStudyType(universityStudyType)
                .domain(domain)
                .studyProgram(studyProgram)
                .studyYear(studyYear)
                .studyGroup(studyGroup)
                .secretaryAllocation(allocation.orElse(null))
                .build();
    }
}
