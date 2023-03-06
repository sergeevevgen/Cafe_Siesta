package client.data.repository;

import client.data.model.entity.Order_Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Order_ItemRepository extends JpaRepository<Order_Item, Long> {
}
