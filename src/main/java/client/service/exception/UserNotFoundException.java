package client.service.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String name) { super(String.format("User with name [%s] is not found", name)); }
}
