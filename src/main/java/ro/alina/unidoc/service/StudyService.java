package ro.alina.unidoc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.alina.unidoc.entity.*;
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

    public List<StudyModel> getAllLearningTypes(){
        return learningTypeRepository.findAll()
                .stream()
                .map(this::toStudyModel)
                .collect(Collectors.toList());
    }

    public List<StudyModel> getAllFilteredLearningTypes(final Long secretaryId){
        return secretaryAllocationRepository.findAllBySecretary_Id(secretaryId)
                .stream()
                .map(SecretaryAllocation::getLearningType)
                .map(this::toStudyModel)
                .collect(Collectors.toList());
    }

    public List<StudyModel> getAllUniversityStudyTypes(final Long learningTypeId){
        return universityStudyTypeRepository.findAllByLearningType_Id(learningTypeId)
                .stream()
                .map(this::toStudyModel)
                .collect(Collectors.toList());
    }

    public List<StudyModel> getAllFilteredUniversityStudyTypes(final Long learningTypeId, final Long secretaryId){
        return secretaryAllocationRepository.findAllBySecretary_IdAndLearningType_Id(secretaryId, learningTypeId)
                .stream()
                .map(SecretaryAllocation::getUniversityStudyType)
                .map(this::toStudyModel)
                .collect(Collectors.toList());
    }

    public List<StudyModel> getAllDomains(final Long universityStudyTypeId){
        return domainRepository.findAllByUniversityStudyType_Id(universityStudyTypeId)
                .stream()
                .map(this::toStudyModel)
                .collect(Collectors.toList());
    }

    public List<StudyModel> getAllFilteredDomains(final Long universityStudyTypeId, final Long secretaryId){
        return secretaryAllocationRepository.findAllBySecretary_IdAndUniversityStudyType_Id(secretaryId, universityStudyTypeId)
                .stream()
                .map(SecretaryAllocation::getDomain)
                .map(this::toStudyModel)
                .collect(Collectors.toList());
    }

    public List<StudyModel> getAllStudyPrograms(final Long domainId){
        return studyProgramRepository.findAllByDomain_Id(domainId)
                .stream()
                .map(this::toStudyModel)
                .collect(Collectors.toList());
    }

    public List<StudyModel> getAllFilteredStudyPrograms(final Long domainId, final Long secretaryId){
        return secretaryAllocationRepository.findAllBySecretary_IdAndDomain_Id(domainId, secretaryId)
                .stream()
                .map(SecretaryAllocation::getStudyProgram)
                .map(this::toStudyModel)
                .collect(Collectors.toList());
    }

    public List<StudyModel> getAllStudyYears(final Long studyProgramId){
        return studyYearRepository.findAllByStudyProgram_Id(studyProgramId)
                .stream()
                .map(this::toStudyModel)
                .collect(Collectors.toList());
    }

    public List<StudyModel> getAllFilteredStudyYears(final Long studyProgramId, final Long secretaryId){
        return secretaryAllocationRepository.findAllBySecretary_IdAndStudyProgram_Id(studyProgramId, secretaryId)
                .stream()
                .map(SecretaryAllocation::getStudyProgram)
                .map(this::toStudyModel)
                .collect(Collectors.toList());
    }

    public List<StudyModel> getAllStudyGroups(final Long studyYearId){
        return studyGroupRepository.findAllByStudyYear_Id(studyYearId)
                .stream()
                .map(this::toStudyModel)
                .collect(Collectors.toList());
    }

    public StudentStudyDetails getStudyDetailsByStudyGroup(final Long studyGroupId){
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

    private StudyModel toStudyModel(final LearningType learningType){
        return StudyModel.builder()
                .id(learningType.getId())
                .value(learningType.getName().toString())
                .build();
    }

    private StudyModel toStudyModel(final UniversityStudyType universityStudyType){
        return StudyModel.builder()
                .id(universityStudyType.getId())
                .value(universityStudyType.getName().toString())
                .build();
    }

    private StudyModel toStudyModel(final Domain domain){
        return StudyModel.builder()
                .id(domain.getId())
                .value(domain.getName().toString())
                .build();
    }

    private StudyModel toStudyModel(final StudyProgram studyProgram){
        return StudyModel.builder()
                .id(studyProgram.getId())
                .value(studyProgram.getName())
                .build();
    }

    private StudyModel toStudyModel(final StudyYear studyYear){
        return StudyModel.builder()
                .id(studyYear.getId())
                .value(studyYear.getName().toString())
                .build();
    }

    private StudyModel toStudyModel(final StudyGroup studyGroup){
        return StudyModel.builder()
                .id(studyGroup.getId())
                .value(studyGroup.getName())
                .build();
    }
}
