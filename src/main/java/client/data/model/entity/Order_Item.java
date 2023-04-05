package client.data.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@EqualsAndHashCode
@ToString
public class Order_Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_fk")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_fk")
    private Order order;

    private Long count;

    public Order_Item() {
    }

    public Order_Item(Long count) {
        this.count = count;
    }

    public Long getId() {
        return id;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        if (!product.getItems().contains(this)) {
            product.addItem(this);
        }
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
        if (!order.getItems().contains(this)) {
            order.setItem(this);
        }
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
