package client.data.repository;

import client.data.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "select * from category c where c.name ilike :name", nativeQuery = true)
    Category findOneByNameIgnoreCase(String name);
}
