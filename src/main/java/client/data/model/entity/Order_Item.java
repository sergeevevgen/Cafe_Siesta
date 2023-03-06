package client.data.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
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

    private Integer count;

    public Order_Item() {
    }

    public Order_Item(Integer count) {
        this.count = count;
    }
}
