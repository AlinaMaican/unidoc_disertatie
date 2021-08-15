package ro.alina.unidoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ro.alina.unidoc.entity.StudentDocument;

public interface StudentDocumentRepository extends JpaRepository<StudentDocument, Long>, JpaSpecificationExecutor<StudentDocument> {
}
