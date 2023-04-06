package client.data.repository;

import client.data.model.entity.Client;
import client.data.model.entity.Order_Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query(value = "select * from client c where c.login = :login", nativeQuery = true)
    Client findByLogin(String login);
}
