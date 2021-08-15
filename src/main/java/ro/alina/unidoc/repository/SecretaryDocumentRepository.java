package ro.alina.unidoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.alina.unidoc.entity.SecretaryDocument;

import java.util.List;

public interface SecretaryDocumentRepository extends JpaRepository<SecretaryDocument, Long> {
    List<SecretaryDocument> findAllBySecretaryAllocation_Id(final Long allocationId);
}
