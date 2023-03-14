package client.mvc;

import client.data.model.dto.ProductDto;
import client.data.model.entity.Category;
import client.service.CategoryService;
import client.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductMvcController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductMvcController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getProducts(Model model) {
        model.addAttribute("products",
                productService.findAllProducts().stream()
                        .map(ProductDto::new)
                        .toList());
        return "index";
    }
    @GetMapping("/login")
    public String getLogin(Model model) {
        model.addAttribute("products",
                productService.findAllProducts().stream()
                        .map(ProductDto::new)
                        .toList());
        return "login";
    }
    @GetMapping("/example")
    public String getExample(Model model) {
        model.addAttribute("products",
                productService.findAllProducts().stream()
                        .map(ProductDto::new)
                        .toList());
        return "example";
    }
    @GetMapping("/signup")
    public String getSignup(Model model) {
        model.addAttribute("products",
                productService.findAllProducts().stream()
                        .map(ProductDto::new)
                        .toList());
        return "signup";
    }
    @GetMapping("/card")
    public String getNavbar(Model model) {
        model.addAttribute("products",
                productService.findAllProducts().stream()
                        .map(ProductDto::new)
                        .toList());
        return "card";
    }
}
