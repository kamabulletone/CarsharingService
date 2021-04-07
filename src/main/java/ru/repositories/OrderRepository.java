package ru.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.model.Order;


public interface OrderRepository extends JpaRepository<Order, String> {
}
