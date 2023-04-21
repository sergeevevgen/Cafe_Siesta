package client.rest_mobile;

import client.configuration.WebConfiguration;
import client.data.model.dto.CategoryDto;
import client.data.model.dto.ComboDto;
import client.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(WebConfiguration.REST_API + "/category")
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

    @PostMapping("/updateOne")
    public CategoryDto updateOne(@RequestBody CategoryDto dto) {
        return categoryService.updateCategory(dto);
    }
}
