package client.service.exception;

public class ReviewNotFoundException extends RuntimeException{
    public ReviewNotFoundException(Long id) { super(String.format("Review with id [%s] is not found", id)); }
}
