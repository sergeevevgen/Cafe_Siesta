package client.service.exception;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(Long id) { super(String.format("Client with id [%s] is not found", id)); }
    public ClientNotFoundException(String login) { super(String.format("Client with login [%s] is not found", login)); }
}
