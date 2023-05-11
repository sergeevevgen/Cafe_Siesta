package client.data.model.entity;

import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@EqualsAndHashCode
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer score;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_fk")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_fk")
    private Client client;

    private Integer liked;

    public Review() {
    }

    public Review(Integer score, String text, Integer like) {
        this.score = score;
        this.text = text;
        this.liked = like;
    }

    public Long getId() {
        return id;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        if (!product.getReviews().contains(this)) {
            product.setReview(this);
        }
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
        if (!client.getReviews().contains(this)) {
            client.setReview(this);
        }
    }

    public Integer getLike() {
        return liked;
    }

    public void setLike(Integer like) {
        this.liked = like;
    }
}
