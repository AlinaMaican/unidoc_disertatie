package ro.alina.unidoc.model;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    ADMIN, SECRETARY, STUDENT;

    @Override
    public String getAuthority() {
        return this.toString();
    }
}
