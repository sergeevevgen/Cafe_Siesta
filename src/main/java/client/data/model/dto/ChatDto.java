package client.data.model.dto;

import client.data.model.entity.Chat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ChatDto {
    private Long id;
    private String title;
    private Long order_id;

    public ChatDto() {
    }

    public ChatDto(Chat chat) {
        this.id = chat.getId();
        this.title = chat.getTitle();
        this.order_id = chat.getOrder().getId();
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Long getOrder_id() {
        return order_id;
    }
}
