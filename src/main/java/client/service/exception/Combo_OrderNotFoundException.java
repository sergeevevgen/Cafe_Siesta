package client.service.exception;

public class Combo_OrderNotFoundException extends RuntimeException{
    public Combo_OrderNotFoundException(Long id) { super(String.format("Combo_Order_Item with id [%s] is not found", id)); }
}
