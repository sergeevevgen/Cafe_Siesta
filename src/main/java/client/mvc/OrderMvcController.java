package client.mvc;

import client.data.model.dto.OrderDto;
import client.data.model.dto.ProductCartDto;
import client.data.model.dto.ProductDto;
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
import org.springframework.web.bind.annotation.*;

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
        if (cartDto.getProducts().isEmpty()) {
            model.addAttribute("products", null);
            return "cart";
        }
        List<ProductDto> products = productService.findProducts(cartDto.getProducts()
                .keySet()
                .stream()
                .toList());
        List<ProductCartDto> productsCart =  new ArrayList<>();
        for (int i = 0; i < cartDto.getProducts().size(); ++i) {
            if (cartDto.getProducts().containsKey(products.get(i).getId())) {
                ProductCartDto productCartDto = new ProductCartDto(products.get(i),
                        cartDto.getProducts().get(products.get(i).getId()));
                productsCart.add(productCartDto);
            }
        }
        model.addAttribute("products", productsCart);
        return "cart";
    }

    @PostMapping("/cart/add/{id}")
    public String addProductToCart(@PathVariable Long id, @RequestParam Long count) {
        try {
            User user = userService.findByLogin(getUserName());
            OrderDto orderDto = orderService.findClientCart(user.getUser_id());
            orderDto.getProducts().put(id, count);
            orderService.updateOrderProducts(orderDto);
            return "redirect:/orders/cart";
        } catch (Exception e) {
            e.printStackTrace(); // Вывод ошибки в консоль
            return "error-page"; // Перенаправление на страницу с сообщением об ошибке
        }
    }

    @PostMapping("/cart/remove/{id}")
    public String removeProductFromCart(@PathVariable Long id) {
        User user = userService.findByLogin(getUserName());

        OrderDto orderDto = orderService.findClientCart(user.getUser_id());
        orderDto.getProducts().remove(id);
        orderService.updateOrderProducts(orderDto);
        return "redirect:/orders/cart";
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
