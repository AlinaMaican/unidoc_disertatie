package ro.alina.unidoc.service;

import lombok.RequiredArgsConstructor;
import org.apache.xmlbeans.impl.xb.xsdschema.All;
import org.springframework.stereotype.Service;
import ro.alina.unidoc.entity.*;
import ro.alina.unidoc.model.AllocationModel;
import ro.alina.unidoc.model.Response;
import ro.alina.unidoc.model.StudentStudyDetails;
import ro.alina.unidoc.model.StudyModel;
import ro.alina.unidoc.repository.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StudyService {

    private final LearningTypeRepository learningTypeRepository;
    private final UniversityStudyTypeRepository universityStudyTypeRepository;
    private final DomainRepository domainRepository;
    private final StudyProgramRepository studyProgramRepository;
    private final StudyYearRepository studyYearRepository;
    private final StudyGroupRepository studyGroupRepository;
    private final SecretaryAllocationRepository secretaryAllocationRepository;
    private final SecretaryRepository secretaryRepository;
    private final StudentDocumentRepository studentDocumentRepository;
    private final SecretaryDocumentRepository secretaryDocumentRepository;
    private final StudentRepository studentRepository;

    public List<StudyModel> getAllLearningTypes() {
        return learningTypeRepository.findAll()
                .stream()
                .map(this::toStudyModel)
                .collect(Collectors.toList());
    }

    public List<StudyModel> getAllFilteredLearningTypes(final Long secretaryId) {
        return secretaryAllocationRepository.findAllBySecretary_Id(secretaryId)
                .stream()
                .map(SecretaryAllocation::getLearningType)
                .map(this::toStudyModel)
                .collect(Collectors.toList());
    }

    public List<StudyModel> getAllUniversityStudyTypes(final Long learningTypeId) {
        return universityStudyTypeRepository.findAllByLearningType_Id(learningTypeId)
                .stream()
                .map(this::toStudyModel)
                .collect(Collectors.toList());
    }

    public List<StudyModel> getAllFilteredUniversityStudyTypes(final Long learningTypeId, final Long secretaryId) {
        return secretaryAllocationRepository.findAllBySecretary_IdAndLearningType_Id(secretaryId, learningTypeId)
                .stream()
                .map(SecretaryAllocation::getUniversityStudyType)
                .map(this::toStudyModel)
                .collect(Collectors.toList());
    }

    public List<StudyModel> getAllDomains(final Long universityStudyTypeId) {
        return domainRepository.findAllByUniversityStudyType_Id(universityStudyTypeId)
                .stream()
                .map(this::toStudyModel)
                .collect(Collectors.toList());
    }

    public List<StudyModel> getAllFilteredDomains(final Long universityStudyTypeId, final Long secretaryId) {
        return secretaryAllocationRepository.findAllBySecretary_IdAndUniversityStudyType_Id(secretaryId, universityStudyTypeId)
                .stream()
                .map(SecretaryAllocation::getDomain)
                .map(this::toStudyModel)
                .collect(Collectors.toList());
    }

    public List<StudyModel> getAllStudyPrograms(final Long domainId) {
        return studyProgramRepository.findAllByDomain_Id(domainId)
                .stream()
                .map(this::toStudyModel)
                .collect(Collectors.toList());
    }

    public List<StudyModel> getAllFilteredStudyPrograms(final Long domainId, final Long secretaryId) {
        return secretaryAllocationRepository.findAllBySecretary_IdAndDomain_Id(secretaryId, domainId)
                .stream()
                .map(SecretaryAllocation::getStudyProgram)
                .map(this::toStudyModel)
                .collect(Collectors.toList());
    }

    public List<StudyModel> getAllStudyYears(final Long studyProgramId) {
        return studyYearRepository.findAllByStudyProgram_Id(studyProgramId)
                .stream()
                .map(this::toStudyModel)
                .collect(Collectors.toList());
    }

    public List<StudyModel> getAllFilteredStudyYears(final Long studyProgramId, final Long secretaryId) {
        return secretaryAllocationRepository.findAllBySecretary_IdAndStudyProgram_Id(secretaryId, studyProgramId)
                .stream()
                .map(SecretaryAllocation::getStudyYear)
                .map(this::toStudyModel)
                .collect(Collectors.toList());
    }

    public List<StudyModel> getAllStudyGroups(final Long studyYearId) {
        return studyGroupRepository.findAllByStudyYear_Id(studyYearId)
                .stream()
                .map(this::toStudyModel)
                .collect(Collectors.toList());
    }

    public StudentStudyDetails getStudyDetailsByStudyGroup(final Long studyGroupId) {
        var studyGroup = studyGroupRepository.getOne(studyGroupId);
        return StudentStudyDetails.builder()
                .studyGroup(studyGroup.getName())
                .studyYear(studyGroup.getStudyYear().getName().toString())
                .studyProgram(studyGroup.getStudyYear().getStudyProgram().getName())
                .domain(studyGroup.getStudyYear().getStudyProgram().getDomain().getName().toString())
                .universityStudyType(studyGroup.getStudyYear().getStudyProgram().getDomain().getUniversityStudyType()
                        .getName().toString())
                .learningType(studyGroup.getStudyYear().getStudyProgram().getDomain().getUniversityStudyType()
                        .getLearningType().getName().toString())
                .build();

    }

    private StudyModel toStudyModel(final LearningType learningType) {
        return StudyModel.builder()
                .id(learningType.getId())
                .value(learningType.getName().toString())
                .build();
    }

    private StudyModel toStudyModel(final UniversityStudyType universityStudyType) {
        return StudyModel.builder()
                .id(universityStudyType.getId())
                .value(universityStudyType.getName().toString())
                .build();
    }

    private StudyModel toStudyModel(final Domain domain) {
        return StudyModel.builder()
                .id(domain.getId())
                .value(domain.getName().toString())
                .build();
    }

    private StudyModel toStudyModel(final StudyProgram studyProgram) {
        return StudyModel.builder()
                .id(studyProgram.getId())
                .value(studyProgram.getName())
                .build();
    }

    private StudyModel toStudyModel(final StudyYear studyYear) {
        return StudyModel.builder()
                .id(studyYear.getId())
                .value(studyYear.getName().toString())
                .build();
    }

    private StudyModel toStudyModel(final StudyGroup studyGroup) {
        return StudyModel.builder()
                .id(studyGroup.getId())
                .value(studyGroup.getName())
                .build();
    }

    public Response createAllocation(final AllocationModel allocationModel) {
        try {
            var optional = secretaryAllocationRepository.findByLearningType_IdAndUniversityStudyType_IdAndDomain_IdAndStudyProgram_IdAndStudyYear_Id(allocationModel.getLearningTypeId(),
                    allocationModel.getUniversityStudyTypeId(), allocationModel.getDomainId(),
                    allocationModel.getStudyProgramId(), allocationModel.getStudyYearId());
            if (optional.isPresent()) {
                return Response.builder()
                        .type("ERROR")
                        .message("The allocation already exists and can not be assigned to this secretary!")
                        .build();
            }
            secretaryAllocationRepository.save(SecretaryAllocation.builder()
                    .secretary(secretaryRepository.getOne(allocationModel.getSecretaryId()))
                    .learningType(learningTypeRepository.getOne(allocationModel.getLearningTypeId()))
                    .universityStudyType(universityStudyTypeRepository.getOne(allocationModel.getUniversityStudyTypeId()))
                    .domain(domainRepository.getOne(allocationModel.getDomainId()))
                    .studyProgram(studyProgramRepository.getOne(allocationModel.getStudyProgramId()))
                    .studyYear(studyYearRepository.getOne(allocationModel.getStudyYearId()))
                    .build());
            return Response.builder()
                    .type("SUCCESS")
                    .message("The allocation was created successfully!")
                    .build();
        } catch (Exception e) {
            return Response.builder()
                    .type("ERROR")
                    .message("Error creating the allocation!")
                    .build();
        }

    }

    public Response deleteAllocation(final Long allocationId) {
        try {
            var allocation = secretaryAllocationRepository.getOne(allocationId);
            var studentDocuments = studentDocumentRepository.findBySecretaryDocument_SecretaryAllocation(allocation);
            var secretaryDocuments = secretaryDocumentRepository.findAllBySecretaryAllocation_Id(allocationId);
            var students = studentRepository.findBySecretaryAllocation(allocation);

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

            secretaryDocumentRepository.deleteAll(secretaryDocuments);

            secretaryAllocationRepository.delete(allocation);
            return Response.builder()
                    .type("SUCCESS")
                    .message("The allocation was deleted successfully!")
                    .build();
        } catch (Exception e) {
            return Response.builder()
                    .type("ERROR")
                    .message("Error creating the allocation!")
                    .build();
        }

    }

    public Response editAllocation(final AllocationModel allocationModel) {
        try {
            var optional = secretaryAllocationRepository.findByLearningType_IdAndUniversityStudyType_IdAndDomain_IdAndStudyProgram_IdAndStudyYear_Id(allocationModel.getLearningTypeId(),
                    allocationModel.getUniversityStudyTypeId(), allocationModel.getDomainId(),
                    allocationModel.getStudyProgramId(), allocationModel.getStudyYearId());
            if (optional.isPresent()) {
                return Response.builder()
                        .type("ERROR")
                        .message("The allocation already exists and can not be assigned to this secretary!")
                        .build();
            }
            var allocation = secretaryAllocationRepository.getOne(allocationModel.getAllocationSecretaryId());
            allocation.setLearningType(learningTypeRepository.getOne(allocationModel.getLearningTypeId()));
            allocation.setUniversityStudyType(universityStudyTypeRepository.getOne(allocationModel.getUniversityStudyTypeId()));
            allocation.setDomain(domainRepository.getOne(allocationModel.getDomainId()));
            allocation.setStudyProgram(studyProgramRepository.getOne(allocationModel.getStudyProgramId()));
            allocation.setStudyYear(studyYearRepository.getOne(allocationModel.getStudyYearId()));
            secretaryAllocationRepository.save(allocation);
            return Response.builder()
                    .type("SUCCESS")
                    .message("The allocation was deleted successfully!")
                    .build();
        } catch (Exception e) {
            return Response.builder()
                    .type("ERROR")
                    .message("Error editing the allocation!")
                    .build();
        }
    }
}
