package ru.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.model.Client;
import ru.model.Order;

import java.util.List;

/**
 * Интерфейс реализующий работу с репозиторием клиента.
 * А именно: поиск клиента по электронной почте,
 * получение списка пользователей без заказаов.
 */
public interface ClientRepository extends JpaRepository<Client, Integer> {

    public Client findByEmail(String email);

    @Query("from Client c where c.clientID not in (Select client from Order o)")
    List<Client> getClientsWithNoOrders();

}
