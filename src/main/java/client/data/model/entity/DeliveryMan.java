package client.data.model.entity;

import client.data.model.enums.DeliveryMan_Status;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@EqualsAndHashCode
public class DeliveryMan {

    //Done
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String surname;

    @NotBlank
    @Column(nullable = false, unique = true)
    //@Email(message = "Email is not valid", regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\\\"(?:[\\\\x01-\\\\x08\\\\x0b\\\\x0c\\\\x0e-\\\\x1f\\\\x21\\\\x23-\\\\x5b\\\\x5d-\\\\x7f]|\\\\\\\\[\\\\x01-\\\\x09\\\\x0b\\\\x0c\\\\x0e-\\\\x7f])*\\\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\\\x01-\\\\x08\\\\x0b\\\\x0c\\\\x0e-\\\\x1f\\\\x21-\\\\x5a\\\\x53-\\\\x7f]|\\\\\\\\[\\\\x01-\\\\x09\\\\x0b\\\\x0c\\\\x0e-\\\\x7f])+)\\\\])")
    private String login;

    @NotBlank
    @Column(nullable = false, length = 64)
    @Size(min = 8, max = 64)
    private String password;

    private String image_url;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DeliveryMan_Status status;

    //done
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "deliveryman_fk")
    private List<Order> orders = new ArrayList<>();

    public DeliveryMan() {
    }

    public DeliveryMan(String name, String surname, String login, String password, String image_url) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.image_url = image_url;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void setOrder(Order order){
        if(!orders.contains(order))
        {
            orders.add(order);
            if(order.getDeliveryMan() != this)
            {
                order.setDeliveryMan(this);
            }
        }
    }

    public Order removeOrder(Long orderId) {
        for (var order : orders) {
            if (Objects.equals(order.getId(), orderId)){
                orders.remove(order);
                return order;
            }
        }
        return null;
    }

    public void removeAllOrders() {
        orders.clear();
    }

    public void updateOrder(Order o) {
        for (var order : orders) {
            if(Objects.equals(order.getId(), o.getId())) {
                order = o;
                return;
            }
        }
    }

    public DeliveryMan_Status getStatus() {
        return status;
    }

    public void setStatus(DeliveryMan_Status status) {
        this.status = status;
    }
}
