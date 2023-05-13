package client.mvc;

import client.data.model.dto.OrderDto;
import client.data.model.entity.User;
import client.service.ClientService;
import client.service.OrderService;
import client.service.ProductService;
import client.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.SystemUtils.getUserName;

@Controller
@RequestMapping("/orders")
public class OrderMvcController {
    private final OrderService orderService;
    private final UserService userService;
    private final ClientService clientService;
    private final ProductService productService;

    private static String getUserName() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return authentication.getName();
    }

    public OrderMvcController(OrderService orderService, UserService userService, ClientService clientService, ProductService productService) {
        this.orderService = orderService;
        this.userService = userService;
        this.clientService = clientService;
        this.productService = productService;
    }

    @GetMapping("/cart")
    public String getCart(Model model) {
        User user = userService.findByLogin(getUserName());
        OrderDto cartDto = orderService.findClientCart(user.getUser_id());
        model.addAttribute("cartDto", cartDto);
        model.addAttribute("products", productService.findProducts(cartDto.getProducts()
                .keySet()
                .stream()
                .toList()));
        return "cart";
    }

    @PostMapping("/cart/{id}")
    public String addProductToCart(@PathVariable Long id) {

        User user = userService.findByLogin(getUserName());


        OrderDto orderDto = orderService.findClientCart(user.getUser_id());
        orderDto.getProducts().put(id, 1L);
        orderDto.setProducts(orderDto.getProducts());
        orderService.updateOrderProducts(orderDto);
        return "redirect:/cart";
    }

    @GetMapping
    public String getOrders(Model model) {

        User user = userService.findByLogin(getUserName());
        model.addAttribute("orders",
                orderService.findAllClientOrders(user.getUser_id()));
        return "orders";
    }

    @GetMapping("/order")
    public String getOrder(Model model) {
        User user = userService.findByLogin(getUserName());
        model.addAttribute("order",
                orderService.findAllClientOrders(user.getUser_id()));
        return "order";
    }
}
