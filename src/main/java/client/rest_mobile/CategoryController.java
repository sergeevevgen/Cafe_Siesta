package client.rest_mobile;

import client.data.model.dto.CategoryDto;
import client.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/getAll")
    public List<CategoryDto> getAllCategories() {
        return categoryService.findAllCategories();
    }

    @PostMapping("/addOne")
    public CategoryDto createOne(@RequestBody CategoryDto dto) {
        return categoryService.addCategory(dto);
    }
}
