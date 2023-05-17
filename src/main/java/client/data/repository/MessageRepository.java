package client.data.repository;

import client.data.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    void deleteAllByChatId(Long chat_id);

    @Query(value = "select * from message m where m.chat_fk = :chat_id", nativeQuery = true)
    List<Message> findAllByChat(Long chat_id);
}
