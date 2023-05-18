package client.data.model.dto;

import client.data.model.entity.Product;

public class ProductCartDto {
    private Long id;
    private String name;
    private String image_url;
    private Long weight;
    private Double price;
    private Long category_id;
    private String category;
    private Long count;
    private Integer isInCart;
    private String description;


    public ProductCartDto() {
    }

    public ProductCartDto(ProductDto product, Long count) {
        this.id = product.getId();
        this.name = product.getName();
        this.image_url = product.getImage_url();
        this.weight = product.getWeight();
        this.price = product.getPrice();
        this.category_id = product.getCategory_id();
        this.category = product.getCategory();
        this.count = count;
    }

    public ProductCartDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.image_url = product.getImage_url();
        this.weight = product.getWeight();
        this.price = product.getPrice();
        this.description = product.getDescription();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public void setName(String name) {
        this.name = name;
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Integer getIsInCart() {
        return isInCart;
    }

    public void setIsInCart(Integer isInCart) {
        this.isInCart = isInCart;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
