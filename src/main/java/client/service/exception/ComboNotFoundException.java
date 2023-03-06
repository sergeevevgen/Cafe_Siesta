package client.service.exception;

public class ComboNotFoundException extends RuntimeException{
    public ComboNotFoundException(Long id) { super(String.format("Combo with id [%s] is not found", id)); }
}
