package ru.controller;



import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.model.*;
import ru.services.*;

import java.security.Principal;
import java.util.List;


@Controller
@AllArgsConstructor
public class MyController {



    @Autowired
    private CarService carService;

    @Autowired
    private OrderService orderService;

//    @Autowired
//    private UserService userService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private MapService mapService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("client", new Client());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    String signUp(@ModelAttribute Client client) {
        client.setDriverLicense("none");
        client.setFacePhoto("none");

        System.out.println(client.toString());

        clientService.signUpUser(client);

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
    public String showCars(Model model, Principal principal) {
        model.addAttribute("cars", carService.getCars());
        System.out.println(principal.getName());
        model.addAttribute("clientName",principal.getName());
        Client client = clientService.getClient(principal.getName());
        System.out.println(client);
       // OrderDto orderDto = new OrderDto()
       // createOrder();
        return "choose";
    }

//    @RequestMapping(value = "/showselectedcar", method = RequestMethod.POST)
//    public String showCarsSubmit(@ModelAttribute Car car) {
//        System.out.println(car.toString());
//        return "redirect:/showcars";
//    }

    @RequestMapping(value = "/showselectedcar", method = RequestMethod.POST)
    public String showCarsSubmit(@RequestParam(value = "carId", required = true) int carId) {
        System.out.println(carId);

        //System.out.println(car.toString());
        return "redirect:/showcars";
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