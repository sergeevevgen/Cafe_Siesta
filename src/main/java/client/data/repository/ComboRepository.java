package client.data.repository;

import client.data.model.entity.Category;
import client.data.model.entity.Combo;
import client.data.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComboRepository extends JpaRepository<Combo, Long> {
    @Query(value = "select * from combo c where c.name ilike :name", nativeQuery = true)
    Combo findOneByNameIgnoreCase(String name);

    @Query(value = "select * from combo c inner join combo_order co on c.id = co.combo_fk inner join orders o on co.order_fk = o.id inner join client cl on o.client_fk = cl.id where cl.id = :id",  nativeQuery = true)
    List<Combo> findCombosByClient(Long id);
}
