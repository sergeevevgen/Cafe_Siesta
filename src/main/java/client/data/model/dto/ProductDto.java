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
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
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
}
