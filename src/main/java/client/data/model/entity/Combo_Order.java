package client.data.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
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

    private Integer count;

    public Combo_Order() {
    }

    public Combo_Order(Integer count) {
        this.count = count;
    }
}
