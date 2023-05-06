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
    private String phone_number;
    private final Set<Long> orders = new HashSet<>();
    public ClientDto() {
    }

    public ClientDto(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.surname = client.getSurname();
        this.login = client.getLogin();
        this.phone_number = client.getPhone_number();
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public void setEntrance(String entrance) {
        this.entrance = entrance;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
