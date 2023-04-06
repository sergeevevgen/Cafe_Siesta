package client.data.model.dto;

import client.data.model.entity.Client;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClientDto {
    private Long id;
    private String name;
    private String surname;
    private String login;
    private String address;
    private Set<Long> orders = new HashSet<>();
    public ClientDto() {
    }

    public ClientDto(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.surname = client.getSurname();
        this.login = client.getLogin();
        this.address = client.getEntrance();
        if (client.getOrders() != null) {
            for (var o : client.getOrders()) {
                orders.add(o.getId());
            }
        }
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
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

    public String getAddress() {
        return address;
    }

    public Set<Long> getOrders() {
        return orders;
    }
}
