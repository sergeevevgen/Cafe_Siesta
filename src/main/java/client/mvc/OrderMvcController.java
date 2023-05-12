package client.mvc;

import client.data.model.dto.OrderDto;
import client.data.model.entity.User;
import client.service.ClientService;
import client.service.OrderService;
import client.service.ProductService;
import client.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/orders")
public class OrderMvcController {
    private final OrderService orderService;
    private final UserService userService;
    private final ClientService clientService;
    private final ProductService productService;

    public OrderMvcController(OrderService orderService, UserService userService, ClientService clientService, ProductService productService) {
        this.orderService = orderService;
        this.userService = userService;
        this.clientService = clientService;
        this.productService = productService;
    }

    @GetMapping("/cart")
    public String getCart(Model model, Principal principal) {
        User user = (User) principal;
        model.addAttribute("cart",
                orderService.findClientCart(user.getUser_id()));
        return "cart";
    }

    @PostMapping("/cart/{id}")
    public String addProductToCart(@PathVariable Long id,
                                   Principal principal) {

        User user = (User) principal;

        OrderDto orderDto = new OrderDto();
        orderDto.setClient_id(user.getUser_id());
        orderDto = orderService.findClientCart(orderDto);
        orderDto.getProducts().put(id, 1L);
        orderDto.setProducts(orderDto.getProducts());
        return "redirect:/cart";
    }

    @GetMapping
    public String getOrders(Model model,
                            Principal principal) {

        User user = (User) principal;
        model.addAttribute("orders",
                orderService.findAllClientOrders(user.getUser_id()));
        return "orders";
    }

    @GetMapping("/order")
    public String getOrder(Model model) {
        model.addAttribute("order",
                orderService.findAllClientOrders(1L));
        return "order";
    }
}
