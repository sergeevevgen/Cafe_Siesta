package client.data.model.dto;

import client.data.model.entity.Combo;

public class ComboCartDto {
    private Long id;
    private String name;
    private String image_url;
    private Double sale;
    private Double price;
    private Long count;
    private Integer isInCart;

    public ComboCartDto() {
    }

    public ComboCartDto(ComboDto comboDto, Long count) {
        this.id = comboDto.getId();
        this.name = comboDto.getName();
        this.image_url = comboDto.getImage_url();
        this.sale = comboDto.getSale();
        this.price = comboDto.getPrice();
        this.count = count;
    }

    public ComboCartDto(Combo combo) {
        this.id = combo.getId();
        this.name = combo.getName();
        this.image_url = combo.getImage_url();
        this.sale = combo.getSale();
        this.price = combo.getPrice();
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

    public Double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public Double getSale() {
        return sale;
    }

    public void setSale(Double sale) {
        this.sale = sale;
    }
}
