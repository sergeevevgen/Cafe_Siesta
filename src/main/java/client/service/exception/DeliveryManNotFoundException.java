package client.service.exception;

public class DeliveryManNotFoundException extends RuntimeException{
    public DeliveryManNotFoundException(Long id) { super(String.format("Deliveryman with id [%s] is not found", id)); }
}
