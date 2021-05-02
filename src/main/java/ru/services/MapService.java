package ru.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.model.Order;
import ru.model.OrderDto;
import ru.repositories.OrderRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MapService {
    @Autowired
    CarService carService;
    @Autowired
    OrderService orderService;
    @Autowired
    ClientService clientService;

    @Autowired
    OrderRepository orderRepository;

    public List<OrderDto> getAllOrders() {
        return ((List<Order>) orderRepository
                .findAll())
                .stream()
                .map(this::convertToOrderDto)
                .collect(Collectors.toList());
    }

    //из order в dto
    public OrderDto convertToOrderDto(Order order){
        OrderDto dto = new OrderDto();
        dto.setCar_id(order.getCar().getCarId());
        dto.setOrder_status_id(order.getOrderStatus().getId());
        dto.setClient_id(order.getClient().getClientID());

        dto.setCar(order.getCar());
        dto.setOrderStatus(order.getOrderStatus());
        dto.setClient(order.getClient());


        dto.setOrder_status(order.getOrderStatus().getDescription());
        dto.setCarMark(order.getCar().getCarMark());
        dto.setCarModel(order.getCar().getCarModel());
        dto.setCarStatus(order.getCar().getCarStatus());

        return dto;
    }
    //из dto в order
    public Order convertToOrder(OrderDto dto){
        Order order = new Order();

        order.setCar(carService.getCar(dto.getCar_id()));
        order.setClient(clientService.getClient(dto.getClient_id()));
        order.setOrderStatus(orderService.getOrderStatus(dto.getOrder_status_id()));

        return order;
    }
}
