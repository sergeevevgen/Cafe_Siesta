package client.data.repository;

import client.data.model.entity.Order;
import client.data.model.entity.Order_Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "select * from orders o where o.client_fk = :clientId", nativeQuery = true)
    List<Order> findByClientId(Long clientId);
}
