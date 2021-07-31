package ro.alina.unidoc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "secretary_allocation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SecretaryAllocation {
    @Id
    @GeneratedValue(generator = "secretary_allocation_seq_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "secretary_allocation_seq_gen", sequenceName = "secretary_allocation_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "secretary_id")
    private Secretary secretary;

    @ManyToOne
    @JoinColumn(name = "learning_type_id")
    private LearningType learningType;

    @ManyToOne
    @JoinColumn(name = "university_study_type_id")
    private UniversityStudyType universityStudyType;

    @ManyToOne
    @JoinColumn(name = "domain_id")
    private Domain domain;

    @ManyToOne
    @JoinColumn(name = "study_program_id")
    private StudyProgram studyProgram;

    @ManyToOne
    @JoinColumn(name = "study_year_id")
    private StudyYear studyYear;

}
