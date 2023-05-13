package client.data.repository;

import client.data.model.entity.Category;
import client.data.model.entity.Combo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ComboRepository extends JpaRepository<Combo, Long> {
    @Query(value = "select * from combo c where c.name ilike :name", nativeQuery = true)
    Combo findOneByNameIgnoreCase(String name);
}
