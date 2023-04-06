package client.data.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@EqualsAndHashCode
public class Combo_Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "combo_fk")
    private Combo combo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_fk")
    private Order order;

    private Long count;

    public Combo_Order() {
    }

    public Combo_Order(Long count) {
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public Combo getCombo() {
        return combo;
    }

    public void setCombo(Combo combo) {
        this.combo = combo;
        if (!combo.getItems().contains(this)) {
            combo.addItem(this);
        }
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
        if (!order.getCombo_items().contains(this)) {
            order.setComboItem(this);
        }
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
