package client.service;

import client.data.model.dto.ReviewDto;
import client.data.model.entity.Client;
import client.data.model.entity.Product;
import client.data.model.entity.Review;
import client.data.repository.ReviewRepository;
import client.service.exception.ReviewNotFoundException;
import client.util.validation.ValidatorUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductService productService;
    private final ValidatorUtil validatorUtil;
    private final ClientService clientService;

    public ReviewService(ReviewRepository reviewRepository, ProductService productService,
                         ValidatorUtil validatorUtil, ClientService clientService) {
        this.reviewRepository = reviewRepository;
        this.validatorUtil = validatorUtil;
        this.productService = productService;
        this.clientService = clientService;
    }

    @Transactional(readOnly = true)
    public Review findReview(Long id) {
        final Optional<Review> review = reviewRepository.findById(id);
        return review.orElseThrow(() -> new ReviewNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public ReviewDto findReviewDto(Long id) {
        return new ReviewDto(findReview(id));
    }

    @Transactional(readOnly = true)
    public List<ReviewDto> findReviewsByProduct(Long id) {
        return reviewRepository.findReviewsByProduct(id)
                .stream()
                .map(ReviewDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ReviewDto> findReviewsByProductNotFacked(Long id) {
        return reviewRepository.findReviewsByProductNotFake(id)
                .stream()
                .map(ReviewDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ReviewDto> findReviewsByClient(Long id) {
        return reviewRepository.findReviewsByClient(id)
                .stream()
                .map(ReviewDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public Review findReviewByClientAndProduct(Long userId, Long productId) {
        return reviewRepository.findReviewByClientAndProduct(userId, productId);
    }

    @Transactional
    public Review addReview(Integer score,
                            String text,
                            Long product_id,
                            Long client_id,
                            Boolean liked) {
        if (product_id == null || product_id <= 0 ||
                client_id == null || client_id <= 0) {
            throw new IllegalArgumentException("Review fields are null or empty");
        }
//        Review review = findReviewByClientAndProduct(client_id, product_id);
//        if (review != null) {
//            return review;
//        }
        Review review = new Review();
        if (score != null) {
            review.setScore(score);
        }
        if (text != null) {
            review.setText(text);
        }
        if (liked != null) {
            review.setLike(liked ? 1 : 0);
        }
        final Client client = clientService.findById(client_id);
        review.setClient(client);
        final Product product = productService.findProduct(product_id);
        review.setProduct(product);
        validatorUtil.validate(review);
        return reviewRepository.save(review);
    }

    @Transactional
    public ReviewDto addReview(ReviewDto reviewDto){
        return new ReviewDto(addReview(reviewDto.getScore(), reviewDto.getText(), reviewDto.getProduct_id(),
                reviewDto.getClient_id(), reviewDto.getLiked()));
    }

    @Transactional
    public Review updateReview(Long id,
                               Integer score,
                               String text,
                               Boolean liked) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Review fields are null or empty");
        }
        final Review review = findReview(id);
        if (review == null) {
            throw new ReviewNotFoundException(id);
        }
        if (score != null) {
            review.setScore(score);
        }
        if (text != null) {
            review.setText(text);
        }
        if (liked != null) {
            review.setLike(liked ? 1 : 0);
        }
        validatorUtil.validate(review);
        return reviewRepository.save(review);
    }

    @Transactional
    public ReviewDto updateOnlyLikeReview(Long id, Boolean liked) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Review fields are null or empty");
        }
        final Review review = findReview(id);
        if (review == null) {
            throw new ReviewNotFoundException(id);
        }
        review.setLike(liked ? 1 : 0);
        validatorUtil.validate(review);
        return new ReviewDto(reviewRepository.save(review));
    }

    @Transactional
    public ReviewDto updateReview(Long id, ReviewDto reviewDto){
        return new ReviewDto(updateReview(id, reviewDto.getScore(), reviewDto.getText(), reviewDto.getLiked()));
    }

    @Transactional
    public Review deleteReview(Long id) {
        Review review = findReview(id);
        reviewRepository.delete(review);
//        final Client client = clientService.findById(review.getClient().getId());
//        client.removeReview(review.getId());
//        final Product product = productService.findProduct(review.getProduct().getId());
//        product.removeReview(review.getId());
        return review;
    }

    @Transactional
    public ReviewDto deleteReview(ReviewDto reviewDto) {
        return new ReviewDto(deleteReview(reviewDto.getId()));
    }
}
