package ro.alina.unidoc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "study_year")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyYear {
    @Id
    @GeneratedValue(generator = "study_year_seq_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "study_year_seq_gen", sequenceName = "study_year_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "study_program_id")
    private StudyProgram studyProgram;

    private String name;
}
