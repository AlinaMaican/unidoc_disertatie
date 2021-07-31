package ro.alina.unidoc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "study_group")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyGroup {
    @Id
    @GeneratedValue(generator = "study_group_seq_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "study_group_seq_gen", sequenceName = "study_group_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "study_year_id")
    private StudyYear studyYear;

    private String name;
}
