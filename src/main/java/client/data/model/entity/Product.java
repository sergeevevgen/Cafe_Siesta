package client.data.model.entity;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@EqualsAndHashCode
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String description;

    private String image_url;

    private Long weight;

    private Double price;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_fk")
    private List<Order_Item> items = new ArrayList<>();

    //Done
    @ManyToOne
    @JoinColumn(name = "category_fk")
    private Category category;

    //Done, maybe, should add many-to-many
    @ManyToOne
    @JoinColumn(name = "combo_fk")
    private Combo combo;

    public Product() {
    }

    public Product(String name, String description, String image_url, Long weight, Double price) {
        this.name = name;
        this.description = description;
        this.image_url = image_url;
        this.weight = weight;
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

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<Order_Item> getItems() {
        return items;
    }

    public void setItems(List<Order_Item> items) {
        this.items = items;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
        if (!category.getProducts().contains(this)) {
            category.setProduct(this);
        }
    }

    public void removeCategory() {
        category.removeProduct(getId());
        category = null;
    }

    public Combo getCombo() {
        return combo;
    }

    public void setCombo(Combo combo) {
        this.combo = combo;
        if (!combo.getProducts().contains(this)) {
            combo.setProduct(this);
        }
    }

    public void removeCombo() {
        combo.removeProduct(getId());
        combo = null;
    }
}
