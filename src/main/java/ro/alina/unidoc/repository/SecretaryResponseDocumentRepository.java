package ro.alina.unidoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.alina.unidoc.entity.SecretaryResponseDocument;
import ro.alina.unidoc.entity.StudentDocument;

import java.util.Optional;

public interface SecretaryResponseDocumentRepository extends JpaRepository<SecretaryResponseDocument, Long> {

    Optional<SecretaryResponseDocument> findByStudentDocument(final StudentDocument studentDocument);
}
