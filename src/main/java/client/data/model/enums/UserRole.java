package client.data.model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    CLIENT,
    MANAGER,
    ADMIN;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
