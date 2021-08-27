package ro.alina.unidoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.alina.unidoc.entity.SecretaryAllocation;
import ro.alina.unidoc.model.type.DomainType;
import ro.alina.unidoc.model.type.LearningTypeEnum;
import ro.alina.unidoc.model.type.UniversityStudyTypeEnum;

import java.util.List;
import java.util.Optional;

public interface SecretaryAllocationRepository extends JpaRepository<SecretaryAllocation, Long> {
    List<SecretaryAllocation> findAllBySecretary_Id(Long secretaryId);

    List<SecretaryAllocation> findAllBySecretary_IdAndLearningType_Id(Long secretaryId, Long learningTypeId);

    List<SecretaryAllocation> findAllBySecretary_IdAndUniversityStudyType_Id(Long secretaryId, Long universityStudyTypeId);

    List<SecretaryAllocation> findAllBySecretary_IdAndDomain_Id(Long secretaryId, Long domainId);

    List<SecretaryAllocation> findAllBySecretary_IdAndStudyProgram_Id(Long secretaryId, Long studyProgramId);

    Optional<SecretaryAllocation> findByLearningType_IdAndUniversityStudyType_IdAndDomain_IdAndStudyProgram_IdAndStudyYear_Id(Long learningTypeId,
                                                                                                                              Long universityStudyTypeId,
                                                                                                                              Long domainId,
                                                                                                                              Long studyProgramId,
                                                                                                                              Long studyYearId);

    List<SecretaryAllocation> findAllBySecretary_IdAndLearningType_NameAndUniversityStudyType_Name(Long secretaryId,
                                                                                                   LearningTypeEnum learningType,
                                                                                                   UniversityStudyTypeEnum universityStudyType);

    List<SecretaryAllocation> findAllBySecretary_IdAndLearningType_NameAndUniversityStudyType_NameAndDomain_Name(Long secretaryId,
                                                                                                                 LearningTypeEnum learningType,
                                                                                                                 UniversityStudyTypeEnum universityStudyType,
                                                                                                                 DomainType domain);

    List<SecretaryAllocation> findAllBySecretary_IdAndLearningType_NameAndUniversityStudyType_NameAndDomain_NameAndStudyProgram_Name(Long secretaryId,
                                                                                                                                     LearningTypeEnum learningType,
                                                                                                                                     UniversityStudyTypeEnum universityStudyType,
                                                                                                                                     DomainType domain, String studyProgram);

    List<SecretaryAllocation> findAllBySecretary_IdAndLearningType_NameAndUniversityStudyType_NameAndDomain_NameAndStudyProgram_NameAndStudyYear_Name(Long secretaryId,
                                                                                                                                                      LearningTypeEnum learningType,
                                                                                                                                                      UniversityStudyTypeEnum universityStudyType,
                                                                                                                                                      DomainType domain, String studyProgram,
                                                                                                                                                      String studyYear);

}
