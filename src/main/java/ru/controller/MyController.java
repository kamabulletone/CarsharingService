package ru.controller;



import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.model.Car;
import ru.model.Order;
import ru.extraservices.EmailService;
import ru.model.User;
import ru.services.CarService;
import ru.services.OrderService;
import ru.services.UserService;

import java.util.List;


@Controller
@AllArgsConstructor
public class MyController {

    @Autowired
    private EmailService m;

    @Autowired
    private CarService a;

    @Autowired
    private OrderService b;

    @Autowired
    private UserService u;

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
        u.signUpUser(user);
        return "redirect:/login";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String getTestPage() {
        return "test";
    }

    @RequestMapping(value = "/home/createitem", method = RequestMethod.POST)
    @ResponseBody
    public void createItem(@RequestBody Car w) {
        m.sendEmailItem(w);
        a.insertItem(w);
    }

    @RequestMapping(value = "/home/createorder", method = RequestMethod.POST)
    @ResponseBody
    public void createOrder(@RequestBody Order w) {
        m.sendEmailOrder(w);
        b.insertOrder(w);
    }

    @RequestMapping(value = "/home/deleteitem", method = RequestMethod.POST)
    @ResponseBody
    public void delItem(@RequestBody Car w) {
        a.deleteItem(w);
    }

    @RequestMapping(value = "/home/deleteorder", method = RequestMethod.POST)
    @ResponseBody
    public void delOrder(@RequestBody Order w) {
        b.deleteOrder(w);
    }

    @RequestMapping(value = "/home/outitem", method = RequestMethod.GET)
    public ResponseEntity<List<Car>> outItem() {
        return new ResponseEntity<List<Car>> (a.getItems(), HttpStatus.OK);
    }

    @RequestMapping(value = "/home/outorder", method = RequestMethod.GET)
    public ResponseEntity<List<Order>> outOrder() {
        return new ResponseEntity<List<Order>> (b.getOrders(), HttpStatus.OK);
    }

    @RequestMapping(value = "home/order/{orderDate}/item", method = RequestMethod.GET)
    @ResponseBody
    public Car getOrderItem(@PathVariable("orderDate") String orderDate) {
        return b.getItemsByOrder(orderDate);
    }

    @RequestMapping(value = "/home/outitem/orderbyname", method = RequestMethod.GET)
    public ResponseEntity<List<Car>> orderByName() {
        return new ResponseEntity<List<Car>>(a.orderByName() , HttpStatus.OK);
    }

    @RequestMapping(value = "/home/outitem/orderbydate", method = RequestMethod.GET)
    public ResponseEntity<List<Car>> orderByDate() {
        return new ResponseEntity<List<Car>>(a.orderByDate() , HttpStatus.OK);
    }

    @RequestMapping(value = "/home/outitem/orderbyprice", method = RequestMethod.GET)
    public ResponseEntity<List<Car>> orderByPrice() {
        return new ResponseEntity<List<Car>>(a.orderByPrice() , HttpStatus.OK);
    }

    @RequestMapping(value = "/home/outorder/orderbydate", method = RequestMethod.GET)
    public ResponseEntity<List<Order>> orderByOrderDate() {
        return new ResponseEntity<List<Order>>(b.orderByOrderDate(), HttpStatus.OK);
    }

    @RequestMapping(value = "/home/outorder/orderbyname", method = RequestMethod.GET)
    public ResponseEntity<List<Order>> orderByItemName() {
        return new ResponseEntity<List<Order>>(b.orderByItemName(), HttpStatus.OK);
    }

}