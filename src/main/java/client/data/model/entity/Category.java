package client.data.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@EqualsAndHashCode
public class Category {
    //Done
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

    //done, messages will delete when order
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "category_fk")
    private List<Product> products = new ArrayList<>();

    public Category() {
    }

    public Category(String name, String description, String image_url) {
        this.name = name;
        this.description = description;
        this.image_url = image_url;
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void updateProduct(Product product) {
        for (var p : products) {
            if (Objects.equals(p.getId(), product.getId())) {
                p = product;
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
            if (product.getCategory() != this) {
                product.setCategory(this);
            }
        }
    }
}
