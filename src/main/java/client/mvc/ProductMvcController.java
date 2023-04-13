package client.mvc;

import client.data.model.dto.ProductDto;
import client.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
public class ProductMvcController {
    private final ProductService productService;

    public ProductMvcController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping
    public String getAllProducts(Model model) {
        model.addAttribute("products",
                productService.findAllProducts());
        return "index";
    }

    @GetMapping("/product/{id}")
    public String getProduct(@PathVariable Long id, Model model) {
        model.addAttribute("product",
                productService.findProduct(id));
        return "product";
    }
}
