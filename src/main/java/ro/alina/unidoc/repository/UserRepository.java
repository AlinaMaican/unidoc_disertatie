package ro.alina.unidoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.alina.unidoc.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndPassword(String username, String password);

    List<User> findAllByIdNotIn(List<Long> id);

    Optional<User> findByEmail(String email);
}
