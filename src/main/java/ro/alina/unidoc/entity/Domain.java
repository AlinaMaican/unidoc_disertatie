package ro.alina.unidoc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.alina.unidoc.model.type.DomainType;


import javax.persistence.*;

@Entity
@Table(name = "domain")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Domain {
    @Id
    @GeneratedValue(generator = "domain_seq_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "domain_seq_gen", sequenceName = "domain_seq", allocationSize = 1)
    private Long id;

    @OneToOne
    @JoinColumn(name = "university_study_type_id")
    private UniversityStudyType universityStudyType;

    @Enumerated(EnumType.STRING)
    private DomainType name;
}
