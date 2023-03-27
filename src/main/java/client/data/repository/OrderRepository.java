package client.data.repository;

import client.data.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "select * from Orders WHERE client_fk = :client and status = :status")
    Order findOneByClientAndStatus(Long client, String status);
}
