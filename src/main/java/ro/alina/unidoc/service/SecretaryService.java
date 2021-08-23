package ro.alina.unidoc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ro.alina.unidoc.entity.Notification;
import ro.alina.unidoc.entity.PhoneNumber;
import ro.alina.unidoc.entity.SecretaryDocument;
import ro.alina.unidoc.entity.StudentDocument;
import ro.alina.unidoc.mapper.SecretaryAllocationMapper;
import ro.alina.unidoc.mapper.SecretaryDocumentMapper;
import ro.alina.unidoc.mapper.StudyDetailsMapper;
import ro.alina.unidoc.model.*;
import ro.alina.unidoc.model.filters.StudentDocumentFilter;
import ro.alina.unidoc.model.type.DocumentStatusType;
import ro.alina.unidoc.repository.*;
import ro.alina.unidoc.utils.GenericSpecification;

import javax.transaction.Transactional;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public List<SecretaryModel> getAllSecretaries() {
        var secretaries = secretaryRepository.findAll();
        List<SecretaryModel> secretaryModels = new ArrayList<>();

        secretaries.forEach(s -> {
            var studyDetails = secretaryAllocationRepository.findAllBySecretary_Id(s.getId())
                    .stream()
                    .map(StudyDetailsMapper::toStudyDetails)
                    .collect(Collectors.toList());
            secretaryModels.add(SecretaryModel.builder()
                    .id(s.getId())
                    .firstName(s.getFirstName())
                    .lastName(s.getLastName())
                    .emailAddress(s.getUser().getEmail())
                    .phoneNumbers(phoneNumberRepository.findAllByUser(s.getUser())
                            .stream()
                            .map(PhoneNumber::getPhoneNumber)
                            .collect(Collectors.toList()))
                    .studyDetails(studyDetails)
                    .build());
        });
        return secretaryModels;
    }

    public List<SecretaryAllocationModel> getSecretaryAllocationsBySecretaryId(final Long secretaryId) {
        return secretaryAllocationRepository.findAllBySecretary_Id(secretaryId)
                .stream()
                .map(SecretaryAllocationMapper::toSecretaryAllocationModel)
                .collect(Collectors.toList());
    }

    public List<SecretaryDocumentModel> getSecretaryDocumentsByAllocationId(final Long allocationId) {
        return secretaryDocumentRepository.findAllBySecretaryAllocation_Id(allocationId)
                .stream()
                .map(SecretaryDocumentMapper::toSecretaryDocumentModel)
                .collect(Collectors.toList());
    }

    public Boolean editSecretaryDocument(final SecretaryDocumentModel model) {
        try {
            secretaryDocumentRepository.findById(model.getId()).ifPresent(secretaryDocument -> {
                secretaryDocument.setName(model.getName());
                secretaryDocument.setDescription(model.getDescription());
                secretaryDocument.setEndDateOfUpload(model.getEndDateOfUpload());
                secretaryDocumentRepository.save(secretaryDocument);
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Response uploadSecretaryDocument(final MultipartFile file, final SecretaryDocumentModel model,
                                            final Long secretaryAllocationId) throws FileAlreadyExistsException {
        if (file.isEmpty()) {
            return Response.builder().type("ERROR").message("The file is empty!").build();
        }
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOAD_FOLDER + "\\" + secretaryAllocationId + "\\" + file.getOriginalFilename());
            Files.write(path, bytes);
            secretaryDocumentRepository.save(SecretaryDocument.builder()
                    .name(model.getName())
                    .description(model.getDescription())
                    .secretaryAllocation(secretaryAllocationRepository.getOne(secretaryAllocationId))
                    .filePathName(UPLOAD_FOLDER + "\\" + secretaryAllocationId + "\\" + file.getOriginalFilename())
                    .endDateOfUpload(model.getEndDateOfUpload())
                    .build());
        } catch (Exception e) {
            return Response.builder().type("ERROR").message("There has been an error").build();
        }
        return Response.builder().type("SUCCESS").message("The file was uploaded successfully!").build();
    }

    public Page<StudentDocumentRowModel> getAllStudentDocuments(final StudentDocumentFilter filter) {
        final var specification = getSpecifications(filter);
        var sort = filter.getSortDirection().equals("desc") ? Sort.by(filter.getColumnName()).descending(): Sort.by(filter.getColumnName()).ascending();
        var pageReq = PageRequest.of(filter.getPageNumber(), filter.getPageSize(), sort);
        return studentDocumentRepository.findAll(specification, pageReq)
                .map(this::toStudentDocumentRowModel);
    }

    public Response editStudentDocumentStatus(final Long documentId, final String status, final String comment) {
        try {
            AtomicReference<String> type = new AtomicReference<>("SUCCESS");
            AtomicReference<String> message = new AtomicReference<>("The status of the file has been changed successfully!");
            studentDocumentRepository.findById(documentId).ifPresent(document -> {
                if (document.getDateOfUpload().isAfter(document.getSecretaryDocument().getEndDateOfUpload())) {
                    type.set("ERROR");
                    message.set("You can not change the status of a document that has the end date exceeded!");
                }
                document.setStatus(DocumentStatusType.valueOf(status));
                studentDocumentRepository.save(document);
                notificationService.sendSimpleMessage(document.getStudent().getUser().getEmail(),
                        "Document status changed", "The status for the document " + document.getName() + " has changed to " + status);
                notificationRepository.save(Notification.builder()
                        .studentDocument(document)
                        .message(comment)
                        .date(LocalDateTime.now())
                        .build());
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
                .and(studentDocumentGenericSpecification.isNestedNestedPropertyEqualNumber("student.secretaryAllocation.secretary.id", filter.getSecretaryId()))
                .and(studentDocumentGenericSpecification.isNestedPropertyEqualNumber("student.learningType.id", filter.getLearningTypeId()))
                .and(studentDocumentGenericSpecification.isNestedPropertyEqualNumber("student.universityStudyType.id", filter.getUniversityStudyId()))
                .and(studentDocumentGenericSpecification.isNestedPropertyEqualNumber("student.domain.id", filter.getDomainId()))
                .and(studentDocumentGenericSpecification.isNestedPropertyEqualNumber("student.studyProgram.id", filter.getStudyProgramId()))
                .and(studentDocumentGenericSpecification.isNestedPropertyEqualNumber("student.studyYear.id", filter.getStudyYearId()))
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
}
