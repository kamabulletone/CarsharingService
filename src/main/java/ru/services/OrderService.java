package ru.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.model.Car;
import ru.model.Order;
import ru.repositories.OrderRepository;


import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class OrderService {

    private  OrderRepository reps;

    public OrderService(OrderRepository reps) {
        this.reps = reps;
    }

    public List<Order> getOrders() {
        log.info("Find all orders");
        return reps.findAll();
    }

    public void insertOrder(Order a) {
        log.info("Save order {}", a);
        reps.save(a);

    }

    public void deleteOrder(Order a) {
        log.info("Delete order {}", a);
        reps.delete(a);
    }

    public Car getItemsByOrder(String orderDate) {
        log.info("Find item by orderDate \"{}\"", orderDate);
        return reps.findById(orderDate).get().getItem();
    }

    public List<Order> orderByOrderDate() {
        log.info("Find all orders order by orderDate");
        return reps.findAll(Sort.by("orderDate"));
    }

    public List<Order> orderByItemName() {
        log.info("Find all orders order by itemName");
        return reps.findAll(Sort.by("itemName"));
    }
}
