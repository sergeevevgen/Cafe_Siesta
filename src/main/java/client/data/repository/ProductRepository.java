package client.data.repository;

import client.data.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findOneByNameIgnoreCase(String name);

    @Query(value = "select * from product p inner join category c on p.category_fk = c.id where c.name = :categoryName",  nativeQuery = true)
    List<Product> findProductsByCategory(String categoryName);

    @Query(value = "select * from product p inner join order_item oi on p.id = oi.product_fk inner join orders o on oi.order_fk = o.id inner join client c on o.client_fk = c.id where c.id = :id",  nativeQuery = true)
    List<Product> findProductsByClient(Long id);

    @Query(value = "select * from product p inner join review r on p.id = r.product_fk inner join client c on r.client_fk = c.id where r.liked = 1 and c.id = :id",  nativeQuery = true)
    List<Product> findLikedProductsByClient(Long id);
}
