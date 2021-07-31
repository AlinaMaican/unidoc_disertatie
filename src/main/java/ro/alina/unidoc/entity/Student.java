package ro.alina.unidoc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "student")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
    @Id
    @GeneratedValue(generator = "student_seq_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "student_seq_gen", sequenceName = "student_seq", allocationSize = 1)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "secretary_allocation_id")
    private SecretaryAllocation secretaryAllocation;

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

    @OneToOne
    @JoinColumn(name = "study_group_id")
    private StudyGroup studyGroup;

    private String firstName;

    private String lastName;

    private String cnp;

    private String registrationNumber;
}
