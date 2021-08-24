package ro.alina.unidoc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

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

    public Page<StudentModel> getAllStudents(final Pageable pageable, final StudentFilter filter) {
        final var specification = getSpecifications(filter);
        return studentRepository.findAll(specification, pageable).map(this::toStudentModel);
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
                return Response.builder().type("ERROR").message("The limit date of the file has been exceded!").build();
            }
            Path path = Paths.get(UPLOAD_FOLDER + "\\" + secretaryDocument.getSecretaryAllocation().getId() + "\\student\\"
                    + studentId + "\\" + file.getOriginalFilename());
            Files.write(path, bytes);
            studentDocumentRepository.findByStudentIdAndSecretaryDocumentId(studentId, secretaryDocumentId).ifPresentOrElse(studentDocument -> {
                studentDocument.setFilePathName(UPLOAD_FOLDER + "\\" + secretaryDocument.getSecretaryAllocation().getId() + "\\student\\"
                        + studentId + "\\" + file.getOriginalFilename());
                studentDocument.setName(name);
                studentDocument.setDateOfUpload(LocalDateTime.now());
                studentDocumentRepository.saveAndFlush(studentDocument);
            }, () -> studentDocumentRepository.save(StudentDocument.builder()
                    .name(name)
                    .student(studentRepository.getOne(studentId))
                    .secretaryDocument(secretaryDocument)
                    .filePathName(UPLOAD_FOLDER + "\\" + secretaryDocument.getSecretaryAllocation().getId() + "\\student\\"
                            + studentId + "\\" + file.getOriginalFilename())
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
        return StudentDocumentModel.builder()
                .name(studentDocument.getName())
                .description(studentDocument.getDescription())
                .dateOfUpload(studentDocument.getDateOfUpload())
                .status(secretaryService.getStatusToANicerForm(studentDocument.getStatus()))
                .id(studentDocument.getId())
                .filePathName(studentDocument.getFilePathName())
                .build();
    }

    public Response uploadOwnDocument(final MultipartFile file, final Long studentId, final String description,
                                      final String name) {
        if (file.isEmpty()) {
            return Response.builder().type("ERROR").message("The file is empty!").build();
        }
        AtomicReference<String> type = new AtomicReference<>("SUCCESS");
        AtomicReference<String> message = new AtomicReference<>("The file was uploaded successfully!");
        try {
            byte[] bytes = file.getBytes();

            Path path = Paths.get(UPLOAD_FOLDER_FOR_OWN_DOCUMENTS + "\\" + studentId + "\\" + file.getOriginalFilename());
            Files.write(path, bytes);
            studentDocumentRepository.findByStudentIdAndFilePathName(studentId, path.toString()).ifPresentOrElse(studentDocument -> {
                type.set("ERROR");
                message.set("The file already exists, it is called " + studentDocument.getName());
            }, () -> {
                var student = studentRepository.getOne(studentId);
                notificationService.sendSimpleMessage(student.getSecretaryAllocation().getSecretary().getUser().getEmail(), "New document added by a student","A new document called " + name +" has been uploaded by the student "
                        + student.getLastName() + " " + student.getFirstName() + " from the group " + student.getStudyGroup().getName());
                var studentDocument = studentDocumentRepository.save(StudentDocument.builder()
                    .name(name)
                    .student(studentRepository.getOne(studentId))
                    .description(description)
                    .filePathName(path.toString())
                    .dateOfUpload(LocalDateTime.now())
                    .documentType(DocumentType.OWN)
                    .status(DocumentStatusType.IN_PROGRESS)
                    .seen(false)
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
                                                                  final String direction){
        final var specification = getNotificationSpecification(studentId);
        var sort = direction.equals("desc")? Sort.by(columnName).descending(): Sort.by(columnName).ascending();
        var pageReq = PageRequest.of(pageNumber, pageSize, sort);
        return notificationRepository.findAll(specification, pageReq)
                .map(this::toStudentNotification);

    }

    private Specification<Notification> getNotificationSpecification(final Long studentId){
        return Objects.requireNonNull(notificationGenericSpecification
                .where(notificationGenericSpecification.isNestedNestedPropertyEqualNumber("studentDocument.student.id", studentId))
        .and(notificationGenericSpecification.isPropertyEqual("type", "STUDENT")));
    }

    private StudentNotificationModel toStudentNotification(final Notification notification){
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

    public Response markNotificationAsSeen(final Long notificationId){
        try{
            notificationRepository.findById(notificationId).ifPresent(notification ->{
                notification.setSeen(true);
                notificationRepository.save(notification);
            });
            return Response.builder()
                    .type("SUCCESS")
                    .message("Notification marked as seen")
                    .build();
        } catch(Exception e){
            return Response.builder()
                    .type("ERROR")
                    .message("Something happened when trying to")
                    .build();
        }
    }

    public int getUnseenNotifications(final Long userId){
        return notificationRepository.countBySeenAndStudentDocument_Student_IdAndType(false, studentRepository.findByUserId(userId).getId(), "STUDENT");
    }

    public StudentModel getUserProfile(final Long userId){
        var student = studentRepository.findByUserId(userId);
        return StudentModel.builder()
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .emailAddress(student.getUser().getEmail())
                .phoneNumbers(phoneNumberRepository.findAllByUser(student.getUser()).stream().map(PhoneNumber::getPhoneNumber).collect(Collectors.toList()))
                .cnp(student.getCnp())
                .registrationNumber(student.getRegistrationNumber())
                .build();
    }
}
