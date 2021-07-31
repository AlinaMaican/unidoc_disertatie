package ro.alina.unidoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.alina.unidoc.entity.StudyProgram;

import java.util.List;

public interface StudyProgramRepository extends JpaRepository<StudyProgram, Long> {
    List<StudyProgram> findAllByDomain_Id(Long domainId);
}
