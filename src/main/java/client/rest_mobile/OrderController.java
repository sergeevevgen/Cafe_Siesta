package client.rest_mobile;

import client.data.model.dto.OrderDto;
import client.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/getAll/{user_id}")
    public String getAllOrders(@PathVariable Long clientId) {
        // все заказы клиента
        return orderService.findAllClientOrders(clientId).toString();
    }

    @GetMapping("/getOne/{order_id}")
    public String getOneOrder(@PathVariable Long id) {
        return orderService.findOrder(id).toString();
    }

    @PostMapping("/addOne/{order_id}")
    public String createOne(@RequestBody OrderDto orderDto) {
        // хочу отменить заказ, нет метода на отмену
        return orderService.addOrder(orderDto).toString();
    }

    @PostMapping("/cancelOne/{order_id}")
    public OrderDto cancelOne(@RequestBody OrderDto orderDto) {
        // хочу отменить заказ, нет метода на отмену
        return orderService.changeOrderStatus(orderDto);
    }
}
