package client.data.model.dto;

import client.data.model.entity.Combo;
import client.data.model.entity.Product;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ComboDto {

    private Long id;
    private String name;
    private String description;
    private String image_url;
    private Double sale;
    private Double price;
    private List<Long> products;

    public ComboDto() {
    }

    public ComboDto(Combo combo) {
        this.id = combo.getId();
        this.name = combo.getName();
        this.description = combo.getDescription();
        this.image_url = combo.getImage_url();
        this.sale = combo.getSale();
        this.price = combo.getPrice();
        if (combo.getProducts() != null) {
            this.products = combo.getProducts().stream().map(Product::getId).toList();
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

    public Double getSale() {
        return sale;
    }

    public Double getPrice() {
        return price;
    }

    public List<Long> getProducts() {
        return products;
    }
}
