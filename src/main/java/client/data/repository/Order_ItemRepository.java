package client.data.repository;

import client.data.model.entity.Order_Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Order_ItemRepository extends JpaRepository<Order_Item, Long> {
    @Query(value = "select * from order_item oi where oi.order_fk = :orderId", nativeQuery = true)
    List<Order_Item> findByOrderId(Long orderId);
}
