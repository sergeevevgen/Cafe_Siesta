package client.data.model.dto;

import client.data.model.entity.Chat;
import client.data.model.entity.Message;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ChatDto {
    private Long id;
    private String title;
    private Long order_id;
    private List<Long> messages;

    public ChatDto() {
    }

    public ChatDto(Chat chat) {
        this.id = chat.getId();
        this.title = chat.getTitle();
        this.order_id = chat.getOrder().getId();
        if (chat.getMessages() != null) {
            this.messages = chat.getMessages().stream().map(Message::getId).toList();
        }
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public List<Long> getMessages() {
        return messages;
    }
}
