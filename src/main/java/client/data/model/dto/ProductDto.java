package client.data.model.dto;

import client.data.model.entity.Product;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private String image_url;
    private Long weight;
    private Double price;
    private Long category_id;
    private String category;
    private Long combo_id;
    private String combo;
    private Integer isInCart;

    public ProductDto() {
    }

    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.image_url = product.getImage_url();
        this.weight = product.getWeight();
        this.price = product.getPrice();
        this.category_id = product.getCategory().getId();
        this.category = product.getCategory().getName();
        if (product.getCombo() != null) {
            this.combo = product.getCombo().getName();
            this.combo_id = product.getCombo().getId();
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage_url() {
        return image_url;
    }

    public Long getWeight() {
        return weight;
    }

    public Double getPrice() {
        return price;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public String getCategory() {
        return category;
    }

    public Long getCombo_id() {
        return combo_id;
    }

    public String getCombo() {
        return combo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCombo_id(Long combo_id) {
        this.combo_id = combo_id;
    }

    public void setCombo(String combo) {
        this.combo = combo;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
