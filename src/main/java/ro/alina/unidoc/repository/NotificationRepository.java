package ro.alina.unidoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ro.alina.unidoc.entity.Notification;
import ro.alina.unidoc.entity.Secretary;

public interface NotificationRepository extends JpaRepository<Notification, Long>, JpaSpecificationExecutor<Notification> {
    int countBySeenAndStudentDocument_Student_IdAndType(final boolean seen, final Long studentId, final String notificationType);

    int countBySeenAndStudentDocument_Student_SecretaryAllocation_SecretaryAndType(final boolean seen,
                                                                                   final Secretary secretary,
                                                                                   final String notificationType);

    Notification findByStudentDocument_IdAndType(final Long documentType, final String type);
}
