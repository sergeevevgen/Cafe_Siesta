package client.service.exception;

public class Order_ItemNotFoundException extends RuntimeException{
    public Order_ItemNotFoundException(Long id) { super(String.format("Item with id [%s] is not found", id)); }
}
