package ro.alina.unidoc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.alina.unidoc.model.type.DocumentStatusType;
import ro.alina.unidoc.model.type.DocumentType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "student_document")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDocument {
    @Id
    @GeneratedValue(generator = "student_document_seq_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "student_document_seq_gen", sequenceName = "student_document_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @OneToOne
    @JoinColumn(name = "secretary_document_id")
    private SecretaryDocument secretaryDocument;

    @Enumerated(EnumType.STRING)
    private DocumentStatusType status;

    private String name;

    private String description;

    private String filePathName;

    private LocalDateTime dateOfUpload;

    private Boolean seen;

    @Enumerated(EnumType.STRING)
    private DocumentType documentType;
}
