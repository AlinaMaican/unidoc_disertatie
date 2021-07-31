package ro.alina.unidoc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponseModel {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String email;
    private List<String> roles;
    private boolean isActive;
}