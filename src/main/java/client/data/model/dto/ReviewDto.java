package client.data.model.dto;


import client.data.model.entity.Review;

public class ReviewDto {

    private Long id;

    private Integer score;

    private String text;

    private Long product_id;

    private String product;

    private Long client_id;

    private String client;

    private Boolean liked;

    public ReviewDto() {

    }

    public ReviewDto(Review review) {
        this.id = review.getId();
        if (review.getScore() != null) {
            this.score = review.getScore();
        }
        if (review.getText() != null) {
            this.text = review.getText();
        }
        if (review.getLike() != null) {
            this.liked = review.getLike() == 1;
        }
        this.product_id = review.getProduct().getId();
        this.product = review.getProduct().getName();
        this.client_id = review.getClient().getId();
        this.client = review.getClient().getName() + ' ' + review.getClient().getSurname();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Long getClient_id() {
        return client_id;
    }

    public void setClient_id(Long client_id) {
        this.client_id = client_id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    public void setIntegerLiked(Integer liked) {
        this.liked = liked == 1;
    }
}
