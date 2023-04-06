package client.rest_mobile;

import client.data.model.dto.CategoryDto;
import client.data.model.dto.ProductDto;
import client.service.CategoryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/getAll")
    public String getAllCategories() {
        return categoryService.findAllCategories().toString();
    }

    @PostMapping("/addOne")
    public String createOne(@RequestBody CategoryDto dto) {
        return categoryService.addCategory(dto).toString();
    }
}
