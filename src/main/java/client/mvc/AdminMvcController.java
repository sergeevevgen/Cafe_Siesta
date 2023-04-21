package client.mvc;

import client.data.model.dto.CategoryDto;
import client.data.model.dto.ProductDto;
import client.service.CategoryService;
import client.service.ComboService;
import client.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminMvcController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ComboService comboService;

    public AdminMvcController(ProductService productService,
                              CategoryService categoryService,
                              ComboService comboService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.comboService = comboService;
    }

    //Products
    @GetMapping("/products")
    public String getAllProducts(Model model) {
        model.addAttribute("products",
                productService.findAllProducts());
        return "products";
    }

    @GetMapping(value = {"/products/edit", "/products/edit/{id}"})
    public String editProduct(@PathVariable(required = false) Long id, Model model) {
        if (id == null || id <= 0) {
            model.addAttribute("productDto", new ProductDto());
        }
        else {
            model.addAttribute("productId", id);
            model.addAttribute("productDto", new ProductDto(productService.findProduct(id)));
        }
        model.addAttribute("categories", categoryService.findAllCategories());
        return "product-edit";
    }

    @PostMapping(value = {"/products/save", "/products/save/{id}"})
    public String saveProduct(@PathVariable(required = false) Long id,
                              @ModelAttribute @Valid ProductDto productDto,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "product-edit";
        }
        if (id == null || id <= 0) {
            productService.addProduct(productDto);
        } else {
            productService.updateProduct(id, productDto);
        }
        return "redirect:/admin/products";
    }

    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }


    //Categories
    @GetMapping("/categories")
    public String getAllCategories(Model model) {
        model.addAttribute("categories",
                categoryService.findAllCategories());
        return "categories";
    }

    @GetMapping(value = {"/categories/edit", "/categories/edit/{id}"})
    public String editCategory(@PathVariable(required = false) Long id, Model model) {
        if (id == null || id <= 0) {
            model.addAttribute("categoryDto", new CategoryDto());
        }
        else {
            model.addAttribute("categoryId", id);
            model.addAttribute("categoryDto", new CategoryDto(categoryService.findById(id)));
        }
        return "category-edit";
    }

    @PostMapping(value = {"/categories/save", "/categories/save/{id}"})
    public String saveCategory(@PathVariable(required = false) Long id,
                              @ModelAttribute @Valid CategoryDto categoryDto,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "category-edit";
        }
        if (id == null || id <= 0) {
            categoryService.addCategory(categoryDto);
        } else {
            categoryService.updateCategory(id, categoryDto);
        }
        return "redirect:/admin/categories";
    }

    @PostMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/admin/categories";
    }
}
