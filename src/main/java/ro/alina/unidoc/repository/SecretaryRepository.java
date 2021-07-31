package ro.alina.unidoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.alina.unidoc.entity.Secretary;

public interface SecretaryRepository extends JpaRepository<Secretary, Long> {
}
