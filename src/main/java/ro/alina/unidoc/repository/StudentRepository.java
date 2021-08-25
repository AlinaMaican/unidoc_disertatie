package ro.alina.unidoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ro.alina.unidoc.entity.SecretaryAllocation;
import ro.alina.unidoc.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {
    Student findByUserId(Long userId);

    Optional<List<Student>> findBySecretaryAllocation(SecretaryAllocation allocation);

    Optional<List<Student>> findBySecretaryAllocation_Secretary_Id(Long secretaryId);
}
