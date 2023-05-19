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

    @GetMapping("/getAllClient/{clientId}")
    public List<OrderDto> getAllOrders(@PathVariable Long clientId) {
        // все заказы клиента
        return orderService.findAllClientOrders(clientId);
    }

    @GetMapping("/getAllDelivery/{deliveryId}")
    public List<OrderDto> getAllDeliveryOrders(@PathVariable Long deliveryId) {
        // все заказы доставщика
        return orderService.findAllDeliveryOrdersEntities(deliveryId);
    }

    @GetMapping("/getAccepted")
    public List<OrderDto> getAcceptedOrders() {
        return orderService.findAcceptedOrders();
    }

    @GetMapping("/getAllOrders")
    public List<OrderDto> getAllOrders() {
        return orderService.findAllOrders();
    }

    @PostMapping("/takeOne")
    public OrderDto takeOrder(@RequestBody OrderDto orderDto) {
        return orderService.updateOrderDeliveryMan(orderDto);
    }

    @GetMapping("/getOne/{id}")
    public OrderDto getOneOrder(@PathVariable Long id) {
        return new OrderDto(orderService.findOrder(id));
    }

    @PostMapping("/addOne")
    public OrderDto createOne(@RequestBody OrderDto orderDto) {
        // хочу отменить заказ, нет метода на отмену
        return orderService.addOrder(orderDto);
    }

    @PostMapping("/changeOrderStatus")
    public OrderDto cancelOne(@RequestBody OrderDto orderDto) {
        // хочу отменить заказ, нет метода на отмену
        return orderService.changeOrderStatus(orderDto);
    }
}
