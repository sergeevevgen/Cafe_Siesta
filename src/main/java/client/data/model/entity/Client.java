package client.data.model.entity;

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
public class Client {

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
    @Column(nullable = false)
    private String phone_number;

    @NotBlank
    @Column(nullable = false, unique = true)
    //@Email(message = "Email is not valid", regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\\\"(?:[\\\\x01-\\\\x08\\\\x0b\\\\x0c\\\\x0e-\\\\x1f\\\\x21\\\\x23-\\\\x5b\\\\x5d-\\\\x7f]|\\\\\\\\[\\\\x01-\\\\x09\\\\x0b\\\\x0c\\\\x0e-\\\\x7f])*\\\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\\\x01-\\\\x08\\\\x0b\\\\x0c\\\\x0e-\\\\x1f\\\\x21-\\\\x5a\\\\x53-\\\\x7f]|\\\\\\\\[\\\\x01-\\\\x09\\\\x0b\\\\x0c\\\\x0e-\\\\x7f])+)\\\\])")
    private String login;

    @NotBlank
    @Column(nullable = false, length = 64)
    @Size(min = 8, max = 64)
    private String password;

    @Column
    private String street;

    @Column
    private String house;

    @Column
    private String flat;

    @Column
    private String entrance;

    //Done
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_fk")
    private List<Order> orders = new ArrayList<>();

    //Done
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_fk")
    private List<Review> reviews = new ArrayList<>();

    public Client() {
    }

    public Client(String name,
                  String surname,
                  String login,
                  String password,
                  String phone_number,
                  String street,
                  String house,
                  String flat,
                  String entrance) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.phone_number = phone_number;
        this.street = street;
        this.house = house;
        this.flat = flat;
        this.entrance = entrance;
    }

    public String getPassword() {
        return password;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getEntrance() {
        return entrance;
    }

    public void setEntrance(String entrance) {
        this.entrance = entrance;
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
            if(order.getClient() != this)
            {
                order.setClient(this);
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
            if (Objects.equals(order.getId(), o.getId())) {
                order = o;
                return;
            }
        }
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void setReview(Review review) {
        if(!reviews.contains(review))
        {
            reviews.add(review);
            if(review.getClient() != this)
            {
                review.setClient(this);
            }
        }
    }

    public Boolean removeReview(Long reviewId) {
        for (var review : reviews) {
            if (Objects.equals(review.getId(), reviewId)){
                reviews.remove(review);
                return true;
            }
        }
        return false;
    }
}
