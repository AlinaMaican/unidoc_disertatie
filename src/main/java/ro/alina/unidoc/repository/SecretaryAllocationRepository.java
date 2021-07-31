package ro.alina.unidoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.alina.unidoc.entity.SecretaryAllocation;

import java.util.List;

public interface SecretaryAllocationRepository extends JpaRepository<SecretaryAllocation, Long> {
    List<SecretaryAllocation> findAllBySecretary_Id(Long secretaryId);
}
