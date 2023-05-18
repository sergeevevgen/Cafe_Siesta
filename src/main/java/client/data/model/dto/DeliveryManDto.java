package client.data.model.dto;

import client.data.model.entity.Client;
import client.data.model.entity.DeliveryMan;
import client.data.model.entity.Order;
import client.data.model.enums.DeliveryMan_Status;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;

public class DeliveryManDto {
    private Long id;
    private String name;
    private String surname;
    private String login;
    private String image_url;
    private DeliveryMan_Status status;
    private String password;
    private String phone_number;
    private final Set<Long> orders = new HashSet<>();

    public DeliveryManDto() {
    }

    public DeliveryManDto(DeliveryMan deliveryMan) {
        this.id = deliveryMan.getId();
        this.name = deliveryMan.getName();
        this.surname = deliveryMan.getSurname();
        this.login = deliveryMan.getLogin();
        this.image_url = deliveryMan.getImage_url();
        this.status = deliveryMan.getStatus();
        this.password = deliveryMan.getPassword();
        this.phone_number = deliveryMan.getPhone_number();
        if (deliveryMan.getOrders() != null) {
            for (var o : deliveryMan.getOrders()) {
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

    public String getImage_url() {
        return image_url;
    }

    public DeliveryMan_Status getStatus() {
        return status;
    }

    public Set<Long> getOrders() {
        return orders;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone_number() {
        return phone_number;
    }
}
