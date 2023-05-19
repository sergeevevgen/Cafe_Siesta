package client.mvc;

import client.data.model.dto.*;
import client.data.model.entity.User;
import client.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.SystemUtils.getUserName;

@Controller
@RequestMapping("/orders")
public class OrderMvcController {
    private final OrderService orderService;
    private final UserService userService;
    private final ClientService clientService;
    private final ComboService comboService;
    private final ProductService productService;

    private static String getUserName() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return authentication.getName();
    }

    public OrderMvcController(OrderService orderService, UserService userService, ClientService clientService, ComboService comboService, ProductService productService) {
        this.orderService = orderService;
        this.userService = userService;
        this.clientService = clientService;
        this.comboService = comboService;
        this.productService = productService;
    }

    @GetMapping("/cart")
    public String getCart(Model model) {
        User user = userService.findByLogin(getUserName());
        OrderDto cartDto = orderService.findClientCart(user.getUser_id());
        model.addAttribute("cartDto", cartDto);

        //Собираем продукты в корзине
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

        //собираем комбо в корзине
        List<ComboDto> combos = comboService.findCombos(cartDto.getCombos()
                .keySet()
                .stream()
                .toList());
        List<ComboCartDto> comboCartDtos =  new ArrayList<>();
        for (int i = 0; i < cartDto.getCombos().size(); ++i) {
            if (cartDto.getCombos().containsKey(combos.get(i).getId())) {
                ComboCartDto comboCartDto = new ComboCartDto(combos.get(i),
                        cartDto.getCombos().get(combos.get(i).getId()));
                comboCartDtos.add(comboCartDto);
            }
        }
        model.addAttribute("combos", comboCartDtos);

        return "cart";
    }

    @PostMapping("/cart/addProduct/{id}")
    public String addProductToCart(@PathVariable Long id, @RequestParam Long count) {
        try {
            User user = userService.findByLogin(getUserName());
            OrderDto orderDto = orderService.findClientCart(user.getUser_id());
            orderDto.getProducts().put(id, count);
            orderService.updateOrderProducts(orderDto);
            return "redirect:/orders/cart";
        } catch (Exception e) {
            e.printStackTrace(); // Вывод ошибки в консоль
            return "redirect:/orders/cart";
        }
    }

    @PostMapping("/cart/removeProduct/{id}")
    public String removeProductFromCart(@PathVariable Long id) {
        User user = userService.findByLogin(getUserName());

        OrderDto orderDto = orderService.findClientCart(user.getUser_id());
        orderDto.getProducts().remove(id);
        orderService.updateOrderProducts(orderDto);
        return "redirect:/orders/cart";
    }

    @PostMapping("/cart/removeAll")
    public String removeAllFromCart() {
        User user = userService.findByLogin(getUserName());

        OrderDto orderDto = orderService.findClientCart(user.getUser_id());
        orderDto.getProducts().clear();
        orderDto.getCombos().clear();
        orderService.updateOrderProducts(orderDto);
        orderService.updateOrderCombos(orderDto);
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

    @PostMapping("/cart/addCombo/{id}")
    public String addComboToCart(@PathVariable Long id, @RequestParam Long count) {
        try {
            User user = userService.findByLogin(getUserName());
            OrderDto orderDto = orderService.findClientCart(user.getUser_id());
            orderDto.getCombos().put(id, count);
            orderService.updateOrderCombos(orderDto);
            return "redirect:/orders/cart";
        } catch (Exception e) {
            e.printStackTrace(); // Вывод ошибки в консоль
            return "redirect:/orders/cart";
        }
    }

    @PostMapping("/cart/removeCombo/{id}")
    public String removeComboFromCart(@PathVariable Long id) {
        User user = userService.findByLogin(getUserName());

        OrderDto orderDto = orderService.findClientCart(user.getUser_id());
        orderDto.getCombos().remove(id);
        orderService.updateOrderCombos(orderDto);
        return "redirect:/orders/cart";
    }
}
