package ru.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.model.Car;
import ru.model.Client;
import ru.model.Order;
import ru.model.OrderStatus;
import ru.repositories.OrderRepository;
import ru.repositories.OrderStatusRepository;


import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class OrderService {

    private  OrderRepository reps;

    @Autowired
    private OrderStatusRepository statReps;

    public OrderService(OrderRepository reps) {
        this.reps = reps;
    }

    /**
     * Метод получения статуса заказа по id
     * @param id идентификатор статуса заказа
     * @return объект статуса заказа
     */
    public OrderStatus getOrderStatus(int id) {
        return statReps.findById(id).get();
    }


    /**
     * Метод получения списка всех заказов
     * @return список заказов
     */
    public List<Order> getOrders() {
        log.info("Find all orders");
        return reps.findAll();
    }

    /**
     * Метод получения последнего заказа по клиенту
     * @param client Объект клиента
     * @return заказ
     */
    public Order getLastOrderByCl(Client client) {
        return reps.getOrderByCl(client);
    }

    /**
     * Метод получения последнего заказа по машине
     * @param car Объект машины
     * @return заказ
     */
    public Car getLastOrderByCar(Car car) {
        return reps.getOrderByCar(car);
    }

    /**
     * Метод добавления заказа
     * @param a Объект заказа
     */
    public void insertOrder(Order a) {
        log.info("Save order {}", a);
        reps.save(a);
    }

    /**
     * Метод удаления заказа
     * @param a Объект заказа
     */
    public void deleteOrder(Order a) {
        log.info("Delete order {}", a);
        reps.delete(a);
    }

    /**
     * Метод обновления статуса заказа
     * @param os объект статуса заказа
     * @param id идентификатор заказа
     */
    public void updateOrderStatus(OrderStatus os, int id) {
        reps.changeOrderStatus(os,id);
    }

    /**
     * Метод получения машины по дате заказа
     * @param orderDate дата заказа
     * @return объект машины
     */
    public Car getCarByOrder(String orderDate) {
        log.info("Find item by orderDate \"{}\"", orderDate);
        return reps.findById(orderDate).get().getCar();
    }

}
