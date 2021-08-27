package ro.alina.unidoc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ro.alina.unidoc.entity.*;
import ro.alina.unidoc.mapper.SecretaryAllocationMapper;
import ro.alina.unidoc.mapper.SecretaryDocumentMapper;
import ro.alina.unidoc.model.*;
import ro.alina.unidoc.model.filters.StudentDocumentFilter;
import ro.alina.unidoc.model.type.DocumentStatusType;
import ro.alina.unidoc.model.type.DomainType;
import ro.alina.unidoc.model.type.LearningTypeEnum;
import ro.alina.unidoc.model.type.UniversityStudyTypeEnum;
import ro.alina.unidoc.repository.*;
import ro.alina.unidoc.utils.GenericSpecification;

import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SecretaryService {
    private final static String UPLOAD_FOLDER = "C:\\Users\\User\\Desktop\\persistence\\secretary\\documents";

    private final SecretaryRepository secretaryRepository;
    private final SecretaryAllocationRepository secretaryAllocationRepository;
    private final SecretaryDocumentRepository secretaryDocumentRepository;
    private final PhoneNumberRepository phoneNumberRepository;
    private final GenericSpecification<StudentDocument> studentDocumentGenericSpecification;
    private final StudentDocumentRepository studentDocumentRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;
    private final UserRepository userRepository;
    private final PasswordGeneratorService passwordGeneratorService;
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;

    public List<SecretaryModel> getAllSecretaries() {
        var secretaries = secretaryRepository.findAll();
        List<SecretaryModel> secretaryModels = new ArrayList<>();

        secretaries.forEach(s -> {
            secretaryModels.add(SecretaryModel.builder()
                    .id(s.getId())
                    .firstName(s.getFirstName())
                    .lastName(s.getLastName())
                    .emailAddress(s.getUser().getEmail())
                    .phoneNumbers(phoneNumberRepository.findAllByUser(s.getUser())
                            .stream()
                            .map(PhoneNumber::getPhoneNumber)
                            .collect(Collectors.toList()))
                    .build());
        });
        return secretaryModels;
    }

    public List<SecretaryAllocationModel> getSecretaryAllocationsBySecretaryId(final Long secretaryId) {
        return secretaryAllocationRepository.findAllBySecretary_Id(secretaryId)
                .stream()
                .map(SecretaryAllocationMapper::toSecretaryAllocationModel)
                .sorted(Comparator.comparing((SecretaryAllocationModel h) -> (h.getLearningType() + ", " + h.getUniversityStudyType()
                        + ", " + h.getDomain() + ", " + h.getStudyProgram() + ", " + h.getStudyYear())))
                .collect(Collectors.toList());
    }

    public List<SecretaryDocumentModel> getSecretaryDocumentsByAllocationId(final Long allocationId) {
        return secretaryDocumentRepository.findAllBySecretaryAllocation_Id(allocationId)
                .stream()
                .map(SecretaryDocumentMapper::toSecretaryDocumentModel)
                .collect(Collectors.toList());
    }

    public Response editSecretaryDocument(final SecretaryDocumentModel model) {
        try {
            secretaryDocumentRepository.findById(model.getId()).ifPresent(secretaryDocument -> {
                secretaryDocument.setName(model.getName());
                secretaryDocument.setDescription(model.getDescription());
                secretaryDocument.setEndDateOfUpload(model.getEndDateOfUpload());
                secretaryDocumentRepository.save(secretaryDocument);
            });
            return Response.builder()
                    .message("The document was edited successfully!")
                    .type("SUCCESS")
                    .build();
        } catch (Exception e) {
            return Response.builder()
                    .message("There was an error editing the document!")
                    .type("ERRORS")
                    .build();
        }
    }

    public Response uploadSecretaryDocument(final MultipartFile file, final SecretaryDocumentModel model) {
        if (file.isEmpty()) {
            return Response.builder().type("ERROR").message("The file is empty!").build();
        }
        List<SecretaryAllocation> allocations;
        if(model.getDomain().equals("ALL")){
            allocations = secretaryAllocationRepository.findAllBySecretary_IdAndLearningType_NameAndUniversityStudyType_Name(model.getSecretaryId(), LearningTypeEnum.valueOf(model.getLearningType()),
                    UniversityStudyTypeEnum.valueOf(model.getUniversityStudy()));
        } else {
            if(model.getStudyProgram().equals("ALL")){
                allocations = secretaryAllocationRepository.findAllBySecretary_IdAndLearningType_NameAndUniversityStudyType_NameAndDomain_Name(model.getSecretaryId(),
                        LearningTypeEnum.valueOf(model.getLearningType()), UniversityStudyTypeEnum.valueOf(model.getUniversityStudy()), DomainType.valueOf(model.getDomain()));
            } else {
                if(model.getStudyYear().equals("ALL")){
                    allocations = secretaryAllocationRepository.findAllBySecretary_IdAndLearningType_NameAndUniversityStudyType_NameAndDomain_NameAndStudyProgram_Name(model.getSecretaryId(),
                            LearningTypeEnum.valueOf(model.getLearningType()), UniversityStudyTypeEnum.valueOf(model.getUniversityStudy()), DomainType.valueOf(model.getDomain()), model.getStudyProgram());
                } else {
                    allocations = secretaryAllocationRepository.findAllBySecretary_IdAndLearningType_NameAndUniversityStudyType_NameAndDomain_NameAndStudyProgram_NameAndStudyYear_Name(model.getSecretaryId(),
                            LearningTypeEnum.valueOf(model.getLearningType()), UniversityStudyTypeEnum.valueOf(model.getUniversityStudy()), DomainType.valueOf(model.getDomain()), model.getStudyProgram(), model.getStudyYear());
                }
            }
        }
        for (SecretaryAllocation allocation : allocations) {
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOAD_FOLDER + "\\" + allocation.getId() + "\\" + file.getOriginalFilename());
                File directory = new File(UPLOAD_FOLDER + "\\" + allocation.getId() + "\\");
                if(!directory.exists())
                {
                    directory.mkdir();
                }
                Files.write(path, bytes);
                secretaryDocumentRepository.save(SecretaryDocument.builder()
                        .name(model.getName())
                        .description(model.getDescription())
                        .secretaryAllocation(secretaryAllocationRepository.getOne(allocation.getId()))
                        .filePathName(path.toString())
                        .endDateOfUpload(model.getEndDateOfUpload())
                        .build());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return Response.builder().type("ERROR").message("There has been an error!").build();
            }
        }
        return Response.builder().type("SUCCESS").message("The file was uploaded successfully!").build();
    }

    public Page<StudentDocumentRowModel> getAllStudentDocuments(final StudentDocumentFilter filter) {
        final var specification = getSpecifications(filter);
        var sort = filter.getSortDirection().equals("desc") ? Sort.by(filter.getColumnName()).descending() : Sort.by(filter.getColumnName()).ascending();
        var pageReq = PageRequest.of(filter.getPageNumber(), filter.getPageSize(), sort);
        return studentDocumentRepository.findAll(specification, pageReq)
                .map(this::toStudentDocumentRowModel);
    }

    public Page<StudentDocumentRowModel> getOwnStudentDocuments(final StudentDocumentFilter filter) {
        final var specification = getSpecificationForOwnDocument(filter);
        var sort = filter.getSortDirection().equals("desc") ? Sort.by(filter.getColumnName()).descending() : Sort.by(filter.getColumnName()).ascending();
        var pageReq = PageRequest.of(filter.getPageNumber(), filter.getPageSize(), sort);
        return studentDocumentRepository.findAll(specification, pageReq)
                .map(this::toOwnStudentDocumentRowModel);
    }

    public Response editStudentDocumentStatus(final Long documentId, final String status, final String comment) {
        try {
            AtomicReference<String> type = new AtomicReference<>("SUCCESS");
            AtomicReference<String> message = new AtomicReference<>("The status of the file has been changed successfully!");
            studentDocumentRepository.findById(documentId).ifPresent(document -> {

                if (document.getSecretaryDocument() != null && document.getDateOfUpload().isAfter(document.getSecretaryDocument().getEndDateOfUpload())) {
                    type.set("ERROR");
                    message.set("You can not change the status of a document that has the end date exceeded!");
                } else {
                    document.setStatus(DocumentStatusType.valueOf(status));
                    var newDocument = studentDocumentRepository.save(document);
                    notificationService.sendSimpleMessage(document.getStudent().getUser().getEmail(),
                            "Document status changed", "The status for the document " + document.getName() + " has changed to " + status);
                    notificationRepository.save(Notification.builder()
                            .studentDocument(newDocument)
                            .message(comment)
                            .date(LocalDateTime.now())
                            .seen(false)
                            .type("STUDENT")
                            .build());
                }
            });
            return Response.builder().type(type.get()).message(message.get()).build();
        } catch (Exception e) {
            return Response.builder().type("ERROR").message("There has been an error").build();
        }
    }

    private Specification<StudentDocument> getSpecifications(final StudentDocumentFilter filter) {
        return Objects.requireNonNull(studentDocumentGenericSpecification.where(
                studentDocumentGenericSpecification.isNestedPropertyLike("student.firstName", filter.getFirstName()))
                .and(studentDocumentGenericSpecification.isNestedPropertyLike("student.lastName", filter.getLastName()))
                .and(studentDocumentGenericSpecification.isNestedPropertyLike("secretaryDocument.name", filter.getName()))
                .and(studentDocumentGenericSpecification.isStatusEqual("status", filter.getStatus()))
                .and(studentDocumentGenericSpecification.isDocumentTypeEqual("documentType", "SECRETARY"))
//                .and(studentDocumentGenericSpecification.isNestedNestedPropertyEqualNumber("student.secretaryAllocation.secretary.id", filter.getSecretaryId()))
//                .and(studentDocumentGenericSpecification.isNestedPropertyEqualNumber("student.learningType.id", filter.getLearningTypeId()))
//                .and(studentDocumentGenericSpecification.isNestedPropertyEqualNumber("student.universityStudyType.id", filter.getUniversityStudyId()))
//                .and(studentDocumentGenericSpecification.isNestedPropertyEqualNumber("student.domain.id", filter.getDomainId()))
//                .and(studentDocumentGenericSpecification.isNestedPropertyEqualNumber("student.studyProgram.id", filter.getStudyProgramId()))
//                .and(studentDocumentGenericSpecification.isNestedPropertyEqualNumber("student.studyYear.id", filter.getStudyYearId()))
                .and(studentDocumentGenericSpecification.isNestedPropertyEqualNumber("student.secretaryAllocation.id", filter.getAllocationId()))
                .and(studentDocumentGenericSpecification.isNestedPropertyEqualNumber("student.studyGroup.id", filter.getStudyGroupId())));
    }

    private Specification<StudentDocument> getSpecificationForOwnDocument(final StudentDocumentFilter filter) {
        return Objects.requireNonNull(studentDocumentGenericSpecification.where(
                studentDocumentGenericSpecification.isNestedPropertyLike("student.firstName", filter.getFirstName()))
                .and(studentDocumentGenericSpecification.isNestedPropertyLike("student.lastName", filter.getLastName()))
                .and(studentDocumentGenericSpecification.isPropertyLike("name", filter.getName()))
                .and(studentDocumentGenericSpecification.isStatusEqual("status", filter.getStatus()))
                .and(studentDocumentGenericSpecification.isDocumentTypeEqual("documentType", "OWN"))
//                .and(studentDocumentGenericSpecification.isNestedNestedPropertyEqualNumber("student.secretaryAllocation.secretary.id", filter.getSecretaryId()))
//                .and(studentDocumentGenericSpecification.isNestedPropertyEqualNumber("student.learningType.id", filter.getLearningTypeId()))
//                .and(studentDocumentGenericSpecification.isNestedPropertyEqualNumber("student.universityStudyType.id", filter.getUniversityStudyId()))
//                .and(studentDocumentGenericSpecification.isNestedPropertyEqualNumber("student.domain.id", filter.getDomainId()))
//                .and(studentDocumentGenericSpecification.isNestedPropertyEqualNumber("student.studyProgram.id", filter.getStudyProgramId()))
//                .and(studentDocumentGenericSpecification.isNestedPropertyEqualNumber("student.studyYear.id", filter.getStudyYearId()))
                .and(studentDocumentGenericSpecification.isNestedPropertyEqualNumber("student.secretaryAllocation.id", filter.getAllocationId()))
                .and(studentDocumentGenericSpecification.isNestedPropertyEqualNumber("student.studyGroup.id", filter.getStudyGroupId())));
    }

    private StudentDocumentRowModel toStudentDocumentRowModel(final StudentDocument studentDocument) {
        return StudentDocumentRowModel.builder()
                .name(studentDocument.getSecretaryDocument().getName())
                .documentId(studentDocument.getId())
                .filePath(studentDocument.getFilePathName())
                .dateOfUpload(studentDocument.getDateOfUpload())
                .status(getStatusToANicerForm(studentDocument.getStatus()))
                .studyGroupId(studentDocument.getStudent().getStudyGroup().getId())
                .studyGroup(studentDocument.getStudent().getStudyGroup().getName())
                .studentModel(StudentModel.builder()
                        .firstName(studentDocument.getStudent().getFirstName())
                        .lastName(studentDocument.getStudent().getLastName())
                        .emailAddress(studentDocument.getStudent().getUser().getEmail())
                        .cnp(studentDocument.getStudent().getCnp())
                        .registrationNumber(studentDocument.getStudent().getRegistrationNumber())
                        .phoneNumbers(phoneNumberRepository.findAllByUser(studentDocument.getStudent().getUser())
                                .stream()
                                .map(PhoneNumber::getPhoneNumber)
                                .collect(Collectors.toList()))
                        .build())
                .build();
    }

    private StudentDocumentRowModel toOwnStudentDocumentRowModel(final StudentDocument studentDocument) {
        return StudentDocumentRowModel.builder()
                .name(studentDocument.getName())
                .documentId(studentDocument.getId())
                .description(studentDocument.getDescription())
                .filePath(studentDocument.getFilePathName())
                .dateOfUpload(studentDocument.getDateOfUpload())
                .status(getStatusToANicerForm(studentDocument.getStatus()))
                .studyGroupId(studentDocument.getStudent().getStudyGroup().getId())
                .studyGroup(studentDocument.getStudent().getStudyGroup().getName())
                .notificationId(notificationRepository.findByStudentDocument_IdAndType(studentDocument.getId(), "SECRETARY").getId())
                .studentModel(StudentModel.builder()
                        .firstName(studentDocument.getStudent().getFirstName())
                        .lastName(studentDocument.getStudent().getLastName())
                        .emailAddress(studentDocument.getStudent().getUser().getEmail())
                        .cnp(studentDocument.getStudent().getCnp())
                        .registrationNumber(studentDocument.getStudent().getRegistrationNumber())
                        .phoneNumbers(phoneNumberRepository.findAllByUser(studentDocument.getStudent().getUser())
                                .stream()
                                .map(PhoneNumber::getPhoneNumber)
                                .collect(Collectors.toList()))
                        .build())
                .build();
    }

    public String getStatusToANicerForm(final DocumentStatusType statusType) {
        switch (statusType) {
            case IN_PROGRESS:
                return "IN PROGRESS";
            case INVALID:
                return "INVALID";
            case VALID:
                return "VALID";
            default:
                throw new IllegalStateException("Unexpected value: " + statusType);
        }
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
        return notificationRepository.countBySeenAndStudentDocument_Student_SecretaryAllocation_SecretaryAndType(false,
                secretaryRepository.findByUserId(userId), "SECRETARY");
    }

    public Response createUserSecretary(final SecretaryModel secretary) {
        try {
            if (userRepository.findByEmail(secretary.getEmailAddress()).isPresent()) {
                return Response.builder()
                        .type("ERROR")
                        .message("There is already a user that has this email address!")
                        .build();
            }
            var password = passwordGeneratorService.generatePassayPassword();
            var user = userRepository.save(User.builder()
                    .email(secretary.getEmailAddress())
                    .isActive(false)
                    .role(UserRole.SECRETARY)
                    .password(passwordEncoder.encode(password))
                    .build());
            notificationService.sendSimpleMessage(secretary.getEmailAddress(), "Generated password for Unidoc", "Hello! Here is your generated password for the app Unidoc. Use it to change your password, not to login in! The password is " + password);
            final var savedUser = userRepository.save(user);
            secretaryRepository.save(Secretary.builder()
                    .user(savedUser)
                    .firstName(secretary.getFirstName())
                    .lastName(secretary.getLastName())
                    .build());
            if (!secretary.getPhoneNumbers().isEmpty()) {
                var phoneNumbers = secretary.getPhoneNumbers().stream()
                        .map(number -> PhoneNumber.builder().phoneNumber(number).user(savedUser).build())
                        .collect(Collectors.toList());
                phoneNumberRepository.saveAll(phoneNumbers);
            }
            return Response.builder()
                    .type("SUCCESS")
                    .message("The user and secretary were created successfully!")
                    .build();
        } catch (Exception e) {
            return Response.builder()
                    .type("ERROR")
                    .message("The user couldn't be saved")
                    .build();
        }
    }

    public Response editUserSecretary(final SecretaryModel secretary) {
        try {
            var secretaryModel = secretaryRepository.getOne(secretary.getId());
            secretaryModel.setLastName(secretary.getLastName());
            secretaryModel.setFirstName(secretary.getFirstName());
            secretaryRepository.save(secretaryModel);
            if (!secretary.getPhoneNumbers().isEmpty()) {
                phoneNumberRepository.deleteAll(phoneNumberRepository.findAllByUser(secretaryModel.getUser()));
                var phoneNumbers = secretary.getPhoneNumbers().stream()
                        .map(number -> PhoneNumber.builder().phoneNumber(number).user(secretaryModel.getUser()).build())
                        .collect(Collectors.toList());
                phoneNumberRepository.saveAll(phoneNumbers);
            }
            return Response.builder()
                    .type("SUCCESS")
                    .message("The user and secretary were edited successfully!")
                    .build();
        } catch (Exception e) {
            return Response.builder()
                    .type("ERROR")
                    .message("The user couldn't be edited")
                    .build();
        }
    }

    public Response deleteUserSecretary(final Long secretaryId){
        try {
            var secretary = secretaryRepository.getOne(secretaryId);
            var phoneNumbers = phoneNumberRepository.findAllByUser(secretary.getUser());
            var allocations = secretaryAllocationRepository.findAllBySecretary_Id(secretaryId);
            var studentDocuments = studentDocumentRepository.findBySecretaryDocument_SecretaryAllocation_Secretary_Id(secretaryId);
            var secretaryDocuments = secretaryDocumentRepository.findAllBySecretaryAllocation_Secretary_Id(secretaryId);
            var students = studentRepository.findBySecretaryAllocation_Secretary_Id(secretaryId);

            students.ifPresent(studentList -> {
                studentRepository.saveAll(studentList.stream()
                        .peek(student -> student.setSecretaryAllocation(null))
                        .collect(Collectors.toList()));
            });

            studentDocuments.ifPresent(studentDocumentList ->
                    studentDocumentRepository.saveAll(studentDocumentList.stream()
                            .peek(studentDocument -> studentDocument.setSecretaryDocument(null))
                            .collect(Collectors.toList())
                    ));

            secretaryDocuments.ifPresent(secretaryDocumentRepository::deleteAll);

            secretaryAllocationRepository.deleteAll(allocations);

            secretaryRepository.delete(secretary);

            phoneNumberRepository.deleteAll(phoneNumbers);

            userRepository.delete(secretary.getUser());

            return Response.builder()
                    .type("SUCCESS")
                    .message("The secretary was deleted successfully!")
                    .build();
        } catch (Exception e){
            return Response.builder()
                    .type("ERROR")
                    .message("Error creating the allocation!")
                    .build();
        }
    }
}
