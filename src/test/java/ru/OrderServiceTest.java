package ru;


import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import ru.model.Car;
import ru.model.Client;
import ru.model.Order;
import ru.repositories.CarRepository;
import ru.repositories.ClientRepository;
import ru.repositories.OrderRepository;
import ru.services.CarService;
import ru.services.ClientService;
import ru.services.OrderService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @InjectMocks
    OrderService orderService;

    @Mock
    OrderRepository orderRepository;

    @Test
    public void getLastOrderByClTest() {
        Client cl = new Client();
        cl.setClientID(1);

        Order o1 = new Order();
        o1.setClient(cl);
        o1.setOrderId(1);

        Order o2 = new Order();
        o2.setClient(cl);
        o2.setOrderId(4);

        Order o3 = new Order();
        o3.setClient(cl);
        o3.setOrderId(3);

        List<Integer> ids = new ArrayList<Integer>();
        ids.add(o2.getOrderId());
        ids.add(o3.getOrderId());
        ids.add(o1.getOrderId());
        Collections.sort(ids);

        List<Order> orders = new ArrayList<Order>();
        orders.add(o2);
        orders.add(o3);
        orders.add(o1);

        Order o = orders.stream().max(Comparator.comparing(Order::getOrderId)).get();


        Mockito.when(orderRepository.getOrderByCl(cl)).thenReturn(o);

        Order res = orderService.getLastOrderByCl(cl);

        Assertions.assertEquals(o2,res);
    }

    @Test
    public void getOrdersTest() {

        Order o1 = new Order();
        o1.setOrderId(1);

        Order o2 = new Order();
        o2.setOrderId(2);

        Order o3 = new Order();
        o3.setOrderId(3);


        List<Order> list = new ArrayList<Order>();

        list.add(o1);
        list.add(o2);
        list.add(o3);

        Mockito.when(orderRepository.findAll()).thenReturn(list);

        List<Order> orders = orderService.getOrders();

        Assertions.assertEquals(3,orders.size());
        Assertions.assertEquals(o1, orders.get(0));
        Assertions.assertEquals(o3, orders.get(2));
        Mockito.verify(orderRepository, times(1)).findAll();

    }

}
