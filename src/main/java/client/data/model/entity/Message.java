package client.data.model.entity;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@EqualsAndHashCode
public class Message {

    //Done
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String text;

    @NotBlank
    @Column(nullable = false)
    private Long time;

    @NotBlank
    @Column(nullable = false)
    private Long sender_id;

    //Done
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_fk")
    private Chat chat;

    public Message() {
    }

    public Message(String text, Long time, Long sender_id) {
        this.text = text;
        this.time = time;
        this.sender_id = sender_id;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getSender_id() {
        return sender_id;
    }

    public void setSender_id(Long sender_id) {
        this.sender_id = sender_id;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
        if (!chat.getMessages().contains(this)) {
            chat.setMessage(this);
        }
    }

    public void removeChat() {
        chat.removeMessage(getId());
        chat = null;
    }
}
