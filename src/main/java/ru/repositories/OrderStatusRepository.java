package ru.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.model.OrderStatus;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Integer> {
}
