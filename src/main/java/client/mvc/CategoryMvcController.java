package client.mvc;

import client.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/category")
public class CategoryMvcController {
    private final CategoryService categoryService;

    public CategoryMvcController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String GetAllCategories(Model model) {
        model.addAttribute("categories",
                categoryService.findAllCategories());
        return "categories-main";
    }
}
