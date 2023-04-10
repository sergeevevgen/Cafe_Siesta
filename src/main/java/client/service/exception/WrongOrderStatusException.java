package client.service.exception;

import client.data.model.enums.Order_Status;

public class WrongOrderStatusException extends IllegalArgumentException {
    public WrongOrderStatusException(Long id, Order_Status status) {
        super(String.format("Order with id [%s] has [%s] status", id, status.toString()));
    }
}
