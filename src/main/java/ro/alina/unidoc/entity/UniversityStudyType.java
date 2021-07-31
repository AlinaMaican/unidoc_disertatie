package ro.alina.unidoc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.alina.unidoc.model.type.UniversityStudyTypeEnum;

import javax.persistence.*;

@Entity
@Table(name = "university_study_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UniversityStudyType {
    @Id
    @GeneratedValue(generator = "university_study_type_seq_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "university_study_type_seq_gen", sequenceName = "university_study_type_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "learning_type_id")
    private LearningType learningType;

    @Enumerated(EnumType.STRING)
    private UniversityStudyTypeEnum name;
}
