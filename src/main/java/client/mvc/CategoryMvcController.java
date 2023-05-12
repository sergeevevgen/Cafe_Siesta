package client.mvc;

import client.data.model.dto.ProductDto;
import client.service.CategoryService;
import client.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryMvcController {
    private final CategoryService categoryService;
    private final ProductService productService;

    public CategoryMvcController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping
    public String getAllCategories(Model model) {
        model.addAttribute("categories",
                categoryService.findAllCategories());
        return "categories-main";
    }

    @GetMapping("/products")
    public String getProductsByCategory(@RequestParam(name = "category") String category, Model model) {
        List<ProductDto> products = productService.findProductsByCategory(category);
        model.addAttribute("products", products);
        return "category";
    }
}
