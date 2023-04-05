package client.rest_html;

import client.service.OrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders_html")
public class HtmlOrderController {
    private final OrderService orderService;

    public HtmlOrderController(OrderService orderService) {
        this.orderService = orderService;
    }
}
