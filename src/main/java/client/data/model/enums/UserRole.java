package client.data.model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    CLIENT,
    MANAGER,
    ADMIN;

    private static final String PREFIX = "ROLE_";

    @Override
    public String getAuthority() {
        return PREFIX + this.name();
    }

    public static final class AsString {
        public static final String ADMIN = PREFIX + "ADMIN";
        public static final String CLIENT = PREFIX + "CLIENT";
        public static final String MANAGER = PREFIX + "MANAGER";
    }

}
