package client.mvc;

import client.data.model.dto.OrderDto;
import client.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders")
public class OrderMvcController {
    private final OrderService orderService;

    public OrderMvcController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/cart")
    public String getCart(Model model) {
        model.addAttribute("cart",
                orderService.findClientCart(1L));
        return "cart";
    }
    @GetMapping
    public String getOrders(Model model) {
        model.addAttribute("orders",
                orderService.findAllClientOrders(1L));
        return "orders";
    }
    @GetMapping("/order")
    public String getOrder(Model model) {
        model.addAttribute("order",
                orderService.findAllClientOrders(1L));
        return "order";
    }

}
