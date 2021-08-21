package ro.alina.unidoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.alina.unidoc.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
