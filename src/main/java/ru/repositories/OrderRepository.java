package ru.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.model.Car;
import ru.model.Client;
import ru.model.Order;
import ru.model.OrderStatus;

/**
 * Интерфейс реализующий работу с репозиторием заказов.
 * А именно: получение последнего заказа по обеъекту клиента, получение последнего заказа по машине
 * изменение статуса заказа по объеку статуса заказа и id заказа.
 */
public interface OrderRepository extends JpaRepository<Order, String> {


    @Query("from Order o where o.orderId = (Select max(orderId) from Order o where o.client=?1)")
    Order getOrderByCl(Client cl);

    @Query("from Order o where o.orderId = (Select max(orderId) from Order o where o.car=?1)")
    Car getOrderByCar(Car car);

    @Modifying
    @Query("update Order o set o.orderStatus = ?1 where o.orderId = ?2")
    int changeOrderStatus(OrderStatus os, Integer id);
}
