package client.mvc;

import client.data.model.dto.ProductCartDto;
import client.data.model.entity.User;
import client.service.CategoryService;
import client.service.ProductService;
import client.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product")
public class ProductMvcController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final UserService userService;

    private static String getUserName() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return authentication.getName();
    }

    public ProductMvcController(ProductService productService, CategoryService categoryService, UserService userService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public String getProduct(@PathVariable Long id, Model model) {
        User user = userService.findByLogin(getUserName());
        ProductCartDto productDto = new ProductCartDto(productService.findProduct(id));
        if (productService.isProductInCart(user.getUser_id(), id)) {
            productDto.setIsInCart(1);
        }
        else {
            productDto.setIsInCart(0);
        }

        model.addAttribute("product", productDto);
        return "product";
    }
}
