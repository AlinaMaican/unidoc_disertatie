package ro.alina.unidoc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserChangePasswordModel {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private String oldPassword;

    @NotBlank
    private String confirmPassword;
}
