package ro.alina.unidoc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "secretary")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Secretary {
    @Id
    @GeneratedValue(generator = "secretary_seq_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "secretary_seq_gen", sequenceName = "secretary_seq", allocationSize = 1)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String firstName;

    private String lastName;
}
