package ro.alina.unidoc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "study_program")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyProgram {
    @Id
    @GeneratedValue(generator = "study_program_seq_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "study_program_seq_gen", sequenceName = "study_program_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "domain_id")
    private Domain domain;

    private String name;
}
