package client.mvc;

import client.data.model.dto.OrderDto;
import client.data.model.dto.ProductCartDto;
import client.data.model.dto.ReviewDto;
import client.data.model.entity.Review;
import client.data.model.entity.User;
import client.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UserService userService;
    private final ReviewService reviewService;
    private final OrderService orderService;

    private static String getUserName() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return authentication.getName();
    }

    public ProductMvcController(ProductService productService, CategoryService categoryService, UserService userService, ReviewService reviewService, OrderService orderService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.reviewService = reviewService;
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public String getProduct(@PathVariable Long id, Model model) {
        User user = userService.findByLogin(getUserName());
        OrderDto cart = orderService.findClientCart(user.getUser_id());
        ProductCartDto productDto = new ProductCartDto(productService.findProduct(id));
        if (productService.isProductInCart(user.getUser_id(), id, cart.getId())) {
            productDto.setIsInCart(1);
        }
        else {
            productDto.setIsInCart(0);
        }

        model.addAttribute("product", productDto);
        model.addAttribute("reviews", reviewService.findReviewsByProductNotFacked(id));

        Review review = reviewService.findReviewByClientAndProduct(user.getUser_id(), id);
        if (review != null) {
            model.addAttribute("reviewClient", new ReviewDto(review));
        }

        return "product";
    }

    @GetMapping(value = {"/{product_id}/review/edit", "/{product_id}/review/edit/{id}"})
    public String makeReview(@PathVariable Long product_id,
                             @PathVariable(required = false) Long id,
                             Model model) {
        if (id == null || id <= 0) {
            model.addAttribute("reviewDto", new ReviewDto());
        }
        else {
            model.addAttribute("reviewId", id);
            model.addAttribute("reviewDto", reviewService.findReviewDto(id));
        }
        model.addAttribute("product_id", product_id);
        return "review-edit";
    }

    @PostMapping(value = {"/{product_id}/review/save", "/{product_id}/review/save/{id}"})
    public String saveReview(@PathVariable Long product_id,
                             @PathVariable(required = false) Long id,
                             @ModelAttribute @Valid ReviewDto reviewDto,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "review-edit";
        }
        User user = userService.findByLogin(getUserName());
        reviewDto.setClient_id(user.getUser_id());
        if (id == null || id <= 0) {
            reviewService.addReview(reviewDto);
        } else {
            reviewService.updateReview(id, reviewDto);
        }
        return "redirect:/product/" + product_id;
    }

    @PostMapping("/{product_id}/review/delete")
    public String deleteReview(@PathVariable Long product_id,
                             Model model) {
        User user = userService.findByLogin(getUserName());
        reviewService.deleteReview(reviewService.findReviewByClientAndProduct(user.getUser_id(), product_id).getId());
        return "redirect:/product/" + product_id;
    }
}
