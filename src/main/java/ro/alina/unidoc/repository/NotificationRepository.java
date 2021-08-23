package ro.alina.unidoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ro.alina.unidoc.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>, JpaSpecificationExecutor<Notification> {
    int countBySeenAndStudentDocument_Student_Id(final boolean seen, final Long studentId);
}
