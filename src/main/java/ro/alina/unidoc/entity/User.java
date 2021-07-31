package ro.alina.unidoc.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.alina.unidoc.model.UserRole;
import ro.alina.unidoc.model.type.UserStatusType;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(generator = "users_seq_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "users_seq_gen", sequenceName = "users_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Enumerated(value= EnumType.STRING)
    private UserRole role;

    @Column(unique=true)
    private String email;

    private String password;

    private Boolean isActive;
}
