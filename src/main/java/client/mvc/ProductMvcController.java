package client.mvc;

import client.data.model.dto.ProductDto;
import client.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductMvcController {
    private final ProductService productService;

    public ProductMvcController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping
    public String getAllCategories(Model model) {
        model.addAttribute("products",
                productService.findAllProducts());
        return "index";
    }
    @GetMapping("/card")
    public String getCard(Model model) {
        model.addAttribute("products",
                productService.findAllProducts());
        return "card";
    }
    @GetMapping("/login")
    public String getLogin(Model model) {
        model.addAttribute("products",
                productService.findAllProducts());
        return "login";
    }
    @GetMapping("/signup")
    public String getSignup(Model model) {
        model.addAttribute("products",
                productService.findAllProducts());
        return "signup";
    }
    @GetMapping("/product")
    public String getProduct(Model model) {
        model.addAttribute("products",
                productService.findAllProducts());
        return "product";
    }
    @GetMapping("/orders")
    public String getOrders(Model model) {
        model.addAttribute("products",
                productService.findAllProducts());
        return "orders";
    }
    @GetMapping("/order")
    public String getOrder(Model model) {
        model.addAttribute("products",
                productService.findAllProducts());
        return "order";
    }
    @GetMapping("/profile")
    public String getProfile(Model model) {
        model.addAttribute("products",
                productService.findAllProducts());
        return "profile";
    }
}
