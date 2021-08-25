package ro.alina.unidoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ro.alina.unidoc.entity.SecretaryAllocation;
import ro.alina.unidoc.entity.Student;
import ro.alina.unidoc.entity.StudentDocument;

import java.util.List;
import java.util.Optional;

public interface StudentDocumentRepository extends JpaRepository<StudentDocument, Long>, JpaSpecificationExecutor<StudentDocument> {
     Optional<StudentDocument> findByStudentIdAndSecretaryDocumentId(Long studentId, Long secretaryDocumentId);

     Optional<StudentDocument> findByStudentIdAndFilePathName(Long studentId, String filePathName);

     Optional<List<StudentDocument>> findBySecretaryDocument_SecretaryAllocation(SecretaryAllocation allocation);

     Optional<List<StudentDocument>> findBySecretaryDocument_SecretaryAllocation_Secretary_Id(Long secretaryId);
}
