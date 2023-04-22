package client.mvc;

import client.service.CategoryService;
import client.service.ComboService;
import client.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class HomeController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ComboService comboService;

    public HomeController(ProductService productService, CategoryService categoryService, ComboService comboService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.comboService = comboService;
    }

    @GetMapping
    public String home(Model model) {
        model.addAttribute("products",
                productService.findAllProducts());
        model.addAttribute("combo",
                comboService.findCombo(1L));
        return "index";
    }
}
