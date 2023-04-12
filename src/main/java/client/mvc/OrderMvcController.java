package client.mvc;

import client.data.model.dto.OrderDto;
import client.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderMvcController {
    private final OrderService orderService;

    public OrderMvcController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/cart")
    public String getCart(Model model) {
        model.addAttribute("cart",
                orderService.findClientCart(14L));
        return "cart";
    }
    @GetMapping("/all")
    public String getOrders(Model model) {
        model.addAttribute("orders",
                orderService.findAllClientOrders(14L));
        return "orders";
    }
    @GetMapping("/order")
    public String getOrder(Model model) {
        model.addAttribute("order",
                orderService.findOrder(1L));
        return "order";
    }

}
