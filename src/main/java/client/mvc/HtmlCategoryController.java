package client.mvc;

import client.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories_html")
public class HtmlCategoryController {
    private final CategoryService categoryService;

    public HtmlCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/c")
    public String getAllCategories() {
        return categoryService.findAllCategories().toString();
    }
}
