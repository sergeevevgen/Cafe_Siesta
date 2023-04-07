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
}
