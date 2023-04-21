package client.data.model.dto;

import client.data.model.entity.Category;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoryDto {

    private Long id;
    private String name;
    private String description;
    private String image_url;

    public CategoryDto() {
    }

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
        this.image_url = category.getImage_url();
    }

    public Long getId() { return id; }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage_url() {
        return image_url;
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
}
