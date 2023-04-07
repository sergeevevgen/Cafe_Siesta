package client.mvc;

import client.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders_html")
public class OrderMvcController {
    private final OrderService orderService;

    public OrderMvcController(OrderService orderService) {
        this.orderService = orderService;
    }
}