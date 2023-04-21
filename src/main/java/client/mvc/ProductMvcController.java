package client.mvc;

import client.data.model.dto.ProductDto;
import client.data.model.entity.Product;
import client.service.CategoryService;
import client.service.ComboService;
import client.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/product")
public class ProductMvcController {
    private final ProductService productService;
    private final CategoryService categoryService;
    public ProductMvcController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public String getProduct(@PathVariable Long id, Model model) {
        model.addAttribute("product",
                productService.findProduct(id));
        return "product";
    }
}
