package ro.alina.unidoc.repository;

import com.nimbusds.oauth2.sdk.auth.Secret;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.alina.unidoc.entity.Secretary;

public interface SecretaryRepository extends JpaRepository<Secretary, Long> {
    Secretary findByUserId(Long userId);
}
