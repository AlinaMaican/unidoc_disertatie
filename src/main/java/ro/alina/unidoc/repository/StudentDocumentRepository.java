package ro.alina.unidoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ro.alina.unidoc.entity.Student;
import ro.alina.unidoc.entity.StudentDocument;

import java.util.Optional;

public interface StudentDocumentRepository extends JpaRepository<StudentDocument, Long>, JpaSpecificationExecutor<StudentDocument> {
     Optional<StudentDocument> findByStudentIdAndSecretaryDocumentId(Long studentId, Long secretaryDocumentId);
}
