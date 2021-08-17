package ro.alina.unidoc.service;

import lombok.RequiredArgsConstructor;
import org.apache.poi.EmptyFileException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public void uploadSecretaryDocument(final MultipartFile file, final SecretaryDocumentModel model,
                                        final Long secretaryAllocationId) throws FileAlreadyExistsException {
        if (file.isEmpty()) {
            throw new EmptyFileException();
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
            throw new FileAlreadyExistsException("lala");
        }
    }

    public Page<StudentDocumentRowModel> getAllStudentDocuments(final Pageable pageable, final StudentDocumentFilter filter) {
        final var specification = getSpecifications(filter);
        return studentDocumentRepository.findAll(specification, pageable).map(this::toStudentDocumentRowModel);
    }

    public Boolean editStudentDocumentStatus(final Long documentId, final String status) {
        try {
            studentDocumentRepository.findById(documentId).ifPresent(document -> {
                document.setStatus(DocumentStatusType.valueOf(status));
                studentDocumentRepository.save(document);
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Specification<StudentDocument> getSpecifications(final StudentDocumentFilter filter) {
        return Objects.requireNonNull(studentDocumentGenericSpecification.where(
                studentDocumentGenericSpecification.isNestedPropertyLike("student.first_name", filter.getFirstName()))
                .and(studentDocumentGenericSpecification.isNestedPropertyLike("student.last_name", filter.getLastName()))
                .and(studentDocumentGenericSpecification.isNestedPropertyLike("secretary_document.name", filter.getFileName()))
                .and(studentDocumentGenericSpecification.isPropertyEqual("status", filter.getStatus()))
                .and(studentDocumentGenericSpecification.isNestedPropertyEqualNumber("student.learning_type_id", filter.getLearningTypeId()))
                .and(studentDocumentGenericSpecification.isNestedPropertyEqualNumber("student.university_study_type_id", filter.getUniversityStudyId()))
                .and(studentDocumentGenericSpecification.isNestedPropertyEqualNumber("student.domain_id", filter.getDomainId()))
                .and(studentDocumentGenericSpecification.isNestedPropertyEqualNumber("student.study_program_id", filter.getStudyProgramId()))
                .and(studentDocumentGenericSpecification.isNestedPropertyEqualNumber("student.study_year_id", filter.getStudyYearId()))
                .and(studentDocumentGenericSpecification.isNestedPropertyEqualNumber("student.study_group_id", filter.getStudyGroupId())));
    }

    private StudentDocumentRowModel toStudentDocumentRowModel(final StudentDocument studentDocument) {
        return StudentDocumentRowModel.builder()
                .fileName(studentDocument.getSecretaryDocument().getFilePathName())
                .filePath(studentDocument.getFilePathName())
                .dateOfUpload(studentDocument.getDateOfUpload())
                .status(studentDocument.getStatus().toString())
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
}
