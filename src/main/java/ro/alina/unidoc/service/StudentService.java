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
import ro.alina.unidoc.entity.Student;
import ro.alina.unidoc.entity.StudentDocument;
import ro.alina.unidoc.model.RequiredDocumentsRowModel;
import ro.alina.unidoc.model.Response;
import ro.alina.unidoc.model.StudentDocumentModel;
import ro.alina.unidoc.model.StudentModel;
import ro.alina.unidoc.model.filters.StudentFilter;
import ro.alina.unidoc.model.type.DocumentStatusType;
import ro.alina.unidoc.model.type.DocumentType;
import ro.alina.unidoc.repository.PhoneNumberRepository;
import ro.alina.unidoc.repository.SecretaryDocumentRepository;
import ro.alina.unidoc.repository.StudentDocumentRepository;
import ro.alina.unidoc.repository.StudentRepository;
import ro.alina.unidoc.utils.GenericSpecification;

import javax.transaction.Transactional;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentService {

    private static final String UPLOAD_FOLDER = "C:\\Users\\User\\Desktop\\persistence\\student\\documents\\secretary";
    private final GenericSpecification<Student> studentGenericSpecification;
    private final StudentRepository studentRepository;
    private final PhoneNumberRepository phoneNumberRepository;
    private final SecretaryDocumentRepository secretaryDocumentRepository;
    private final StudentDocumentRepository studentDocumentRepository;
    private final SecretaryService secretaryService;

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
            if(secretaryDocument.getEndDateOfUpload().isBefore(LocalDateTime.now())){
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
            }, ()-> studentDocumentRepository.save(StudentDocument.builder()
                    .name(name)
                    .student(studentRepository.getOne(studentId))
                    .secretaryDocument(secretaryDocument)
                    .filePathName(UPLOAD_FOLDER + "\\" + secretaryDocument.getSecretaryAllocation().getId() + "\\student\\"
                            + studentId + "\\" + file.getOriginalFilename())
                    .dateOfUpload(LocalDateTime.now())
                    .documentType(DocumentType.CERERE)
                    .status(DocumentStatusType.IN_PROGRESS)
                    .build()));
        } catch (Exception e) {
            return Response.builder().type("ERROR").message("There has been an error").build();
        }
        return Response.builder().type("SUCCESS").message("The file was uploaded successfully!").build();
    }
}
