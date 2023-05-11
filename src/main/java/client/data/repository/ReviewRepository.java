package client.data.repository;

import client.data.model.entity.Product;
import client.data.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = "select * from review r inner join product p on r.product_fk = p.id where p.name = :productName",  nativeQuery = true)
    List<Review> findReviewsByProduct(String productName);
}
