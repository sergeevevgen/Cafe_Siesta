package client.rest_mobile;

import client.configuration.WebConfiguration;
import client.data.model.dto.OrderDto;
import client.data.model.entity.Order;
import client.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(WebConfiguration.REST_API + "/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/getAll/{user_id}")
    public List<OrderDto> getAllOrders(@PathVariable Long clientId) {
        // все заказы клиента
        return orderService.findAllClientOrders(clientId);
    }

    @GetMapping("/getOne/{order_id}")
    public Order getOneOrder(@PathVariable Long id) {
        return orderService.findOrder(id);
    }

    @PostMapping("/addOne/{order_id}")
    public OrderDto createOne(@RequestBody OrderDto orderDto) {
        // хочу отменить заказ, нет метода на отмену
        return orderService.addOrder(orderDto);
    }

    @PostMapping("/cancelOne/{order_id}")
    public OrderDto cancelOne(@RequestBody OrderDto orderDto) {
        // хочу отменить заказ, нет метода на отмену
        return orderService.changeOrderStatus(orderDto);
    }
}
