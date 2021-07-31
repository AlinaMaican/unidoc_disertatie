package ro.alina.unidoc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "secretary_document")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SecretaryDocument {
    @Id
    @GeneratedValue(generator = "secretary_document_seq_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "secretary_document_seq_gen", sequenceName = "secretary_document_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "secretary_allocation_id")
    private SecretaryAllocation secretaryAllocation;

    private String name;

    private String description;

    private String filePathName;

    private LocalDateTime endDateOfUpload;
}
