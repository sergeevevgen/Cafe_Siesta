package client.data.repository;

import client.data.model.entity.Chat;
import client.data.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query(value = "select * from chat c inner join orders o on o.chat_id = c.id where o.client_fk = :clientId", nativeQuery = true)
    List<Chat> findByClientId(Long clientId);
}
