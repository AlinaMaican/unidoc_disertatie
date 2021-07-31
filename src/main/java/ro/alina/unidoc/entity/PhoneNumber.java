package ro.alina.unidoc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "phone_number")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneNumber {
    @Id
    @GeneratedValue(generator = "phone_number_seq_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "phone_number_seq_gen", sequenceName = "phone_number_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String phoneNumber;
}
