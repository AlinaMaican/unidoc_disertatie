package ro.alina.unidoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.alina.unidoc.entity.StudyYear;

import java.util.List;

public interface StudyYearRepository extends JpaRepository<StudyYear, Long> {
    List<StudyYear> findAllByStudyProgram_Id(Long studyProgramId);
}
