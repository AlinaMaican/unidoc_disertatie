package ro.alina.unidoc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "secretary_response_document")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SecretaryResponseDocument {
    @Id
    @GeneratedValue(generator = "secretary_response_document_seq_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "secretary_response_document_seq_gen", sequenceName = "secretary_document_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "secretary_allocation_id")
    private SecretaryAllocation secretaryAllocation;

    @ManyToOne
    @JoinColumn(name = "student_document_id")
    private StudentDocument studentDocument;

    private String name;

    private String description;

    private String filePathName;

    private LocalDateTime dateOfUpload;
}
