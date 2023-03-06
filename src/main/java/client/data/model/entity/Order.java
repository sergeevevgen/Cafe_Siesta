package client.data.model.entity;

import client.data.model.enums.Order_Status;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@EqualsAndHashCode
@ToString
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Order_Status status;

    private Double price;

    @Transient
    private Integer count;

    @NotBlank
    @Column(nullable = false)
    private String title;

    //done
    @ManyToOne
    @JoinColumn(name = "client_fk")
    private Client client;

    //done
    @ManyToOne
    @JoinColumn(name = "deliveryman_fk")
    private DeliveryMan deliveryMan;

    //done, items will delete when order
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_fk")
    private List<Order_Item> items = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_fk")
    private List<Combo> combos = new ArrayList<>();

    @OneToOne(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Chat chat;

    public Order() {
    }

    public Order(Order_Status status, Double price, Integer count, String title) {
        this.status = status;
        this.price = price;
        this.count = count;
        this.title = title;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
        if (chat.getOrder() != this) {
            chat.setOrder(this);
        }
    }

    public Long getId() {
        return id;
    }

    public Order_Status getStatus() {
        return status;
    }

    public void setStatus(Order_Status status) {
        this.status = status;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
        if (!client.getOrders().contains(this)) {
            client.setOrder(this);
        }
    }

    public void removeClient() {
        client.removeOrder(getId());
        client = null;
    }

    public void removeDeliveryMan() {
        deliveryMan.removeOrder(getId());
        deliveryMan = null;
    }

    public DeliveryMan getDeliveryMan() {
        return deliveryMan;
    }

    public void setDeliveryMan(DeliveryMan deliveryMan) {
        this.deliveryMan = deliveryMan;
        if (!deliveryMan.getOrders().contains(this)) {
            deliveryMan.setOrder(this);
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCount() {
        int k = 0;
        for (var i : items) {
            k += i.getCount();
        }
        return k;
    }

    public List<Order_Item> getItems() {
        return items;
    }

    public void setItems(List<Order_Item> items) {
        this.items = items;
    }

    public List<Combo> getCombos() {
        return combos;
    }

    public void setCombos(List<Combo> combos) {
        this.combos = combos;
    }

    public void updateCombo(Combo c) {
        for (var m : combos) {
            if (Objects.equals(m.getId(), c.getId())) {
                m = c;
                return;
            }
        }
    }
    public Combo removeCombo(Long id) {
        for (var c : combos) {
            if (Objects.equals(c.getId(), id)) {
                combos.remove(c);
                return c;
            }
        }
        return null;
    }

    public void setCombo(Combo combo) {
        if (!combos.contains(combo)) {
            combos.add(combo);
            if (combo.getOrder() != this) {
                combo.setOrder(this);
            }
        }
    }

    public void updateItems(Order_Item item) {
        for (var i : items) {
            if (Objects.equals(i.getId(), item.getId())) {
                i = item;
                return;
            }
        }
    }
    public Order_Item removeItems(Long id) {
        for (var i : items) {
            if (Objects.equals(i.getId(), id)) {
                items.remove(i);
                return i;
            }
        }
        return null;
    }

    public void setItem(Order_Item item) {
        if (!items.contains(item)) {
            items.add(item);
            if (item.getOrder() != this) {
                item.setOrder(this);
            }
        }
    }
}
