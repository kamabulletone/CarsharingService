package ru.controller;



import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.model.Car;
import ru.model.Order;
import ru.model.OrderDto;
import ru.model.User;
import ru.services.CarService;
import ru.services.MapService;
import ru.services.OrderService;
import ru.services.UserService;

import java.util.List;


@Controller
@AllArgsConstructor
public class MyController {



    @Autowired
    private CarService carService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private MapService mapService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(User user) {
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    String signUp(@ModelAttribute User user) {
        userService.signUpUser(user);
        return "redirect:/login";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String getTestPage() {
        return "test";
    }

    @RequestMapping(value = "/home/createcar", method = RequestMethod.POST)
    @ResponseBody
    public void createItem(@RequestBody Car w) {

        carService.insertCar(w);
    }

    @RequestMapping(value = "/map", method = RequestMethod.GET)
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return new ResponseEntity<List<OrderDto>> (mapService.getAllOrders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/showcars" , method = RequestMethod.GET)
    public String showCars(Model model) {
        model.addAttribute("cars", carService.getCars());
        return "choose";
    }

//    @RequestMapping(value = "/home/createorder", method = RequestMethod.POST)
//    @ResponseBody
//    public void createOrder(@RequestBody Order w) {
//
//        orderService.insertOrder(w);
//    }

    @RequestMapping(value = "/home/createorder", method = RequestMethod.POST)
    @ResponseBody
    public void createOrder(@RequestBody OrderDto orderDto) {
        System.out.println(orderDto.toString());
        Order order = mapService.convertToOrder(orderDto);
        orderService.insertOrder(order);
    }

    @RequestMapping(value = "/home/deleteitem", method = RequestMethod.POST)
    @ResponseBody
    public void delItem(@RequestBody Car w) {
        carService.deleteCar(w);
    }

    @RequestMapping(value = "/home/deleteorder", method = RequestMethod.POST)
    @ResponseBody
    public void delOrder(@RequestBody Order w) {
        orderService.deleteOrder(w);
    }

    @RequestMapping(value = "/home/outcar", method = RequestMethod.GET)
    public ResponseEntity<List<Car>> outItem() {
        return new ResponseEntity<List<Car>> (carService.getCars(), HttpStatus.OK);
    }

    @RequestMapping(value = "/home/outorder", method = RequestMethod.GET)
    public ResponseEntity<List<Order>> outOrder() {
        return new ResponseEntity<List<Order>> (orderService.getOrders(), HttpStatus.OK);
    }

    @RequestMapping(value = "home/order/{orderDate}/item", method = RequestMethod.GET)
    @ResponseBody
    public Car getOrderItem(@PathVariable("orderDate") String orderDate) {
        return orderService.getCarByOrder(orderDate);
    }

//    @RequestMapping(value = "/home/outitem/orderbyname", method = RequestMethod.GET)
//    public ResponseEntity<List<Car>> orderByName() {
//        return new ResponseEntity<List<Car>>(carService.orderByName() , HttpStatus.OK);
//    }
//
//    @RequestMapping(value = "/home/outitem/orderbydate", method = RequestMethod.GET)
//    public ResponseEntity<List<Car>> orderByDate() {
//        return new ResponseEntity<List<Car>>(carService.orderByDate() , HttpStatus.OK);
//    }
//
//    @RequestMapping(value = "/home/outitem/orderbyprice", method = RequestMethod.GET)
//    public ResponseEntity<List<Car>> orderByPrice() {
//        return new ResponseEntity<List<Car>>(carService.orderByPrice() , HttpStatus.OK);
//    }
//
//    @RequestMapping(value = "/home/outorder/orderbydate", method = RequestMethod.GET)
//    public ResponseEntity<List<Order>> orderByOrderDate() {
//        return new ResponseEntity<List<Order>>(orderService.orderByOrderDate(), HttpStatus.OK);
//    }
//
//    @RequestMapping(value = "/home/outorder/orderbyname", method = RequestMethod.GET)
//    public ResponseEntity<List<Order>> orderByItemName() {
//        return new ResponseEntity<List<Order>>(orderService.orderByItemName(), HttpStatus.OK);
//    }

}