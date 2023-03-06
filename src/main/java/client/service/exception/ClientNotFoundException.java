package client.service.exception;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(Long id) { super(String.format("Client with id [%s] is not found", id)); }
}
