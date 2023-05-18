package client.rest_mobile;

import client.configuration.WebConfiguration;
import client.data.model.dto.ProductDto;
import client.data.model.dto.ReviewDto;
import client.service.ProductService;
import client.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(WebConfiguration.REST_API + "/review")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/getAllByProduct/{id}")
    public List<ReviewDto> getAllReviewsByProduct(@PathVariable Long id) {
        return reviewService.findReviewsByProduct(id);
    }

    @GetMapping("/getAllByClient/{id}")
    public List<ReviewDto> getAllReviewsByClient(@PathVariable Long id) {
        return reviewService.findReviewsByClient(id);
    }

    @GetMapping("/getOne/{id}")
    public ReviewDto getOneReview(@PathVariable Long id) {
        return reviewService.findReviewDto(id);
    }

    @PostMapping("/addOne")
    public ReviewDto createOne(@RequestBody ReviewDto reviewDto) {
        return reviewService.addReview(reviewDto);

    }

    @PostMapping("/updateOne/{id}")
    public ReviewDto updateOne(@PathVariable Long id, @RequestBody ReviewDto reviewDto) {
        return reviewService.updateReview(id, reviewDto);
    }

    @PostMapping("/updateOneOnlyLike/{id}")
    public ReviewDto updateOneOnlyLike(@PathVariable Long id, @RequestBody ReviewDto reviewDto) {
        return reviewService.updateOnlyLikeReview(id, reviewDto);
    }
}