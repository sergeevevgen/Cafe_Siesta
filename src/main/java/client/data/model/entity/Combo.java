package client.data.model.entity;

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
public class Combo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String description;

    private String image_url;

    @Transient
    private Double sale;

    @NotBlank
    @Column(nullable = false)
    private Double price;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "combo_fk")
    private List<Product> products = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "combo_fk")
    private List<Combo_Order> items = new ArrayList<>();

    public Combo() {
    }

    public Combo(String name, String description, String image_url, Double sale, Double price) {
        this.name = name;
        this.description = description;
        this.image_url = image_url;
        this.sale = sale;
        this.price = price;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Double getSale() {
        return sale;
    }

    public void setSale(Double sale) {
        this.sale = sale;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void updateProduct(Product product) {
        for (var m : products) {
            if (Objects.equals(m.getId(), product.getId())) {
                m = product;
                return;
            }
        }
    }
    public Product removeProduct(Long id) {
        for (var p : products) {
            if (Objects.equals(p.getId(), id)) {
                products.remove(p);
                return p;
            }
        }
        return null;
    }

    public void setProduct(Product product) {
        if (!products.contains(product)) {
            products.add(product);
            if (product.getCombo() != this) {
                product.setCombo(this);
            }
        }
    }

    public List<Combo_Order> getItems() {
        return items;
    }

    public void setItems(List<Combo_Order> items) {
        this.items = items;
    }
}
