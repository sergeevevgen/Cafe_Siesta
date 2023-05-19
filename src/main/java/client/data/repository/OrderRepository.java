package client.data.repository;

import client.data.model.entity.Order;
import client.data.model.entity.Order_Item;
import client.data.model.enums.Order_Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "select * from orders o where o.client_fk = :clientId and o.status <> 'Is_cart'", nativeQuery = true)
    List<Order> findByClientId(Long clientId);

    @Query(value = "select * from orders o where o.deliveryman_fk = :deliveryId", nativeQuery = true)
    List<Order> findByDeliveryId(Long deliveryId);

    @Query(value = "select * from orders o where o.client_fk = :clientId and o.status = 'Is_cart'", nativeQuery = true)
    Order findCartByClient(Long clientId);

    @Query(value = "select * from orders where status = 'Accepted'", nativeQuery = true)
    List<Order> findOrdersAccepted();

    @Query(value = "select * from orders where status <> 'Is_cart'", nativeQuery = true)
    List<Order> findOrdersNotCarts();
}
