package ru.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.model.OrderStatus;

/**
 * Интерфейс реализующий работу с репозиторием статусов заказа.
 */
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Integer> {
}
