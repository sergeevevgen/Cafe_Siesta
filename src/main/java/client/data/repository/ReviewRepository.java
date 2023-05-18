package client.data.repository;

import client.data.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = "select * from review r inner join product p on r.product_fk = p.id where p.id = :productId",  nativeQuery = true)
    List<Review> findReviewsByProduct(Long productId);
    @Query(value = "select * from review r inner join product p on r.product_fk = p.id where r.client_fk = :clientId",  nativeQuery = true)
    List<Review> findReviewsByClient(Long clientId);
    @Query(value = "select * from review r inner join product p on r.product_fk = p.id where r.client_fk = :clientId and p.id = :productId", nativeQuery = true)
    List<Review> findReviewByClientAndProduct(Long clientId, Long productId);
}
