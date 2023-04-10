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
    private String password;
    private String street;
    private String flat;
    private String entrance;
    private String house;
    private final Set<Long> orders = new HashSet<>();
    public ClientDto() {
    }

    public ClientDto(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.surname = client.getSurname();
        this.login = client.getLogin();
        if (client.getStreet() != null) {
            this.street = client.getStreet();
        }
        if (client.getHouse() != null) {
            this.house = client.getHouse();
        }
        if (client.getFlat() != null) {
            this.flat = client.getFlat();
        }
        if (client.getEntrance() != null) {
            this.entrance = client.getEntrance();
        }


        this.password = client.getPassword();
        if (client.getOrders() != null) {
            for (var o : client.getOrders()) {
                orders.add(o.getId());
            }
        }
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

    public String getStreet() {
        return street;
    }

    public String getPassword() {
        return password;
    }

    public String getFlat() {
        return flat;
    }

    public String getEntrance() {
        return entrance;
    }

    public String getHouse() {
        return house;
    }

    public Set<Long> getOrders() {
        return orders;
    }
}
