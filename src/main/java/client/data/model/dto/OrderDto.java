package client.data.model.dto;

import client.data.model.entity.Combo_Order;
import client.data.model.entity.Order;
import client.data.model.entity.Order_Item;
import client.data.model.enums.Order_Status;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class OrderDto {

    private Long id;
    private Order_Status status;
    private Double price;
    private String title;
    private Integer count;
    private Long client_id;
    private Long deliveryman_id;
    private List<Long> items = new ArrayList<>();
    private List<Long> combo_items = new ArrayList<>();
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
        this.deliveryman_id = order.getDeliveryMan().getId();
        this.chat_id = order.getChat().getId();
        if (order.getItems() != null) {
            this.items = order.getItems().stream().map(Order_Item::getId).toList();
        }
        if (order.getCombo_items() != null) {
            this.combo_items = order.getCombo_items().stream().map(Combo_Order::getId).toList();
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

    public List<Long> getItems() {
        return items;
    }

    public List<Long> getCombo_items() {
        return combo_items;
    }

    public Long getChat_id() {
        return chat_id;
    }
}
