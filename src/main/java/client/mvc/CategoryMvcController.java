package client.mvc;

import client.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories_html")
public class CategoryMvcController {
    private final CategoryService categoryService;

    public CategoryMvcController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/c")
    public String getAllCategories() {
        return categoryService.findAllCategories().toString();
    }
}
