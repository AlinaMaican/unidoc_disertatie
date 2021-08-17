package ro.alina.unidoc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.alina.unidoc.model.type.LearningTypeEnum;

import javax.persistence.*;

@Entity
@Table(name = "learning_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LearningType {
    @Id
    @GeneratedValue(generator = "learning_type_seq_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "learning_type_seq_gen", sequenceName = "learning_type_seq", allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    private LearningTypeEnum name;
}
