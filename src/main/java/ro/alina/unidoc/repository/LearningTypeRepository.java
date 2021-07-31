package ro.alina.unidoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.alina.unidoc.entity.LearningType;

public interface LearningTypeRepository extends JpaRepository<LearningType, Long> {
}
