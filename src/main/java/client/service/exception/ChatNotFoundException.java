package client.service.exception;

public class ChatNotFoundException extends RuntimeException{
    public ChatNotFoundException(Long id) { super(String.format("Chat with id [%s] is not found", id)); }
}
