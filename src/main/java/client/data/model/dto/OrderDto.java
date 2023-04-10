package client.data.model.dto;

import client.data.model.entity.Combo_Order;
import client.data.model.entity.Order;
import client.data.model.entity.Order_Item;
import client.data.model.enums.Order_Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import groovy.lang.Tuple;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDto {

    private Long id;
    private Order_Status status;
    private Double price;
    private String title;
    private Integer count;
    private Long client_id;
    private Long deliveryman_id;
    private String street;
    private String house;
    private String flat;
    private String entrance;

    private final Map<Long, Long> products = new HashMap<>();
    private final Map<Long, Long> combos = new HashMap<>();
    private Long chat_id;

    public OrderDto() {
    }

    public OrderDto(Order order) {
        this.id = order.getId();
        this.status = order.getStatus();
        this.price = order.getPrice();
        this.title = order.getTitle();
        this.count = order.getCount();
        this.client_id = order.getClient().getId();
        if (order.getStreet() != null) {
            this.street = order.getStreet();
        }
        if (order.getHouse() != null) {
            this.house = order.getHouse();
        }
        if (order.getFlat() != null) {
            this.flat = order.getFlat();
        }
        if (order.getEntrance() != null) {
            this.entrance = order.getEntrance();
        }

        if (order.getDeliveryMan() != null) {
            this.deliveryman_id = order.getDeliveryMan().getId();
        }
        if (order.getChat() != null) {
            this.chat_id = order.getChat().getId();
        }

        if (order.getItems() != null) {
            List<Order_Item> items = order.getItems();
            for (var i : items) {
                products.put(i.getProduct().getId(), i.getCount());
            }
        }
        if (order.getCombo_items() != null) {
            List<Combo_Order> items = order.getCombo_items();
            for (var i : items) {
                combos.put(i.getCombo().getId(), i.getCount());
            }
        }
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Long getId() {
        return id;
    }

    public Order_Status getStatus() {
        return status;
    }

    public Double getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public Integer getCount() {
        return count;
    }

    public Long getClient_id() {
        return client_id;
    }

    public Long getDeliveryman_id() {
        return deliveryman_id;
    }

    public Map<Long, Long> getProducts() {
        return products;
    }

    public Map<Long, Long> getCombos() {
        return combos;
    }

    public Long getChat_id() {
        return chat_id;
    }

    public String getStreet() {
        return street;
    }

    public String getHouse() {
        return house;
    }

    public String getFlat() {
        return flat;
    }

    public String getEntrance() {
        return entrance;
    }
}
