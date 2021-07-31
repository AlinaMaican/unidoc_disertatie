package ro.alina.unidoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.alina.unidoc.entity.UniversityStudyType;

import java.util.List;

public interface UniversityStudyTypeRepository extends JpaRepository<UniversityStudyType, Long> {
    List<UniversityStudyType> findAllByLearningType_Id(Long learningTypeId);
}
