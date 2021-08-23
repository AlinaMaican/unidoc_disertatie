package ro.alina.unidoc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
    @Id
    @GeneratedValue(generator = "notification_seq_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "notification_seq_gen", sequenceName = "notification_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_document_id")
    private StudentDocument studentDocument;

    private String message;

    private LocalDateTime date;

    private Boolean seen;

    private String type;
}
