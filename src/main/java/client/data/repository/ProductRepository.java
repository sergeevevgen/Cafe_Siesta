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
}
