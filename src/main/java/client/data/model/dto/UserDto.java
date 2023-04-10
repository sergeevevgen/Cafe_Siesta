package client.data.model.dto;

import client.data.model.entity.User;
import client.data.model.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDto {
    private Long id;
    private String name;
    private String surname;
    private String login;
    private UserRole role;
    private Long user_id;

    public UserDto() {
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.login = user.getLogin();
        this.role = user.getRole();
        this.user_id = user.getUser_id();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getLogin() {
        return login;
    }

    public UserRole getRole() {
        return role;
    }

    public Long getUser_id() {
        return user_id;
    }
}
