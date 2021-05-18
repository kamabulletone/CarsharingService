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
import java.util.ArrayList;
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
    String showhome(Model model, Principal principal) {

        String role = clientService.getClient(principal.getName()).getAuthorities().size() == 1 ? "ROLE_USER" : "ROLE_ADMIN";
        model.addAttribute("role", role);
        System.out.println(clientService.getClient(principal.getName()).getAuthorities() + " " + role + "");

        return "home";
    }


    @RequestMapping(value = "/home/finishorder", method = RequestMethod.GET)
    public String finishOrderV(Model model) {

        return "finishOrder";
    }

    @RequestMapping(value = "/home/finishorder", method = RequestMethod.POST)
    public String finishOrder(@RequestParam(value="cost", required = true) String cost, Principal principal ) {
        System.out.println("cost = " + cost);

        Client cl = clientService.getClient(principal.getName());
        Order o = orderService.getLastOrderByCl(cl);
        OrderStatus os = orderService.getOrderStatus(2); //finished

        orderService.updateOrderStatus(os, o.getOrderId());
        carService.updateStatus("free",o.getCar().getCarId());




        return  "redirect:/home";

    }



    @RequestMapping(value = "/home/deletecar", method = RequestMethod.POST)
    public String deleteCar(@RequestParam(value = "cars") int[] carsId) {
        for (int carid:carsId
        ) {
            System.out.println(carid);
            carService.deleteById(carid);
        }
        return "redirect:/home/delete";

    }

    @RequestMapping(value = "/home/deleteclient", method = RequestMethod.POST)
    public String deleteClient(@RequestParam(value = "clients") int[] clientsId) {
        for (int clientId:clientsId
        ) {
            System.out.println(clientId);
            clientService.deleteById(clientId);
        }
        return "redirect:/home/delete";

    }

    @RequestMapping(value = "/home/delete", method = RequestMethod.GET)
    public String getDeleteV(Model model) {
        model.addAttribute("clients", clientService.getClients());
        System.out.println(clientService.getClients());
        model.addAttribute("cars", carService.getCars());


        return "delete";

    }

    @RequestMapping(value = "/home/createorder" , method = RequestMethod.GET)
    public String showCars(Model model, Principal principal) {
        model.addAttribute("order", new Order());
        model.addAttribute("cars", carService.getCars());
        System.out.println(principal.getName());
        model.addAttribute("clientName",principal.getName());
        Client client = clientService.getClient(principal.getName());
        System.out.println(client);
        // OrderDto orderDto = new OrderDto()
        // createOrder();
        return "choose";
    }



    @RequestMapping(value = "/home/testcars" , method = RequestMethod.GET)
    public String testuser(Model model, Principal principal) {
        model.addAttribute("order", new Order());
        model.addAttribute("cars", carService.getCars());
        System.out.println(principal.getName());
        model.addAttribute("clientName",principal.getName());
        Client client = clientService.getClient(principal.getName());
        System.out.println(client);
        // OrderDto orderDto = new OrderDto()
        // createOrder();
        return "test";
    }



    @RequestMapping(value = "/home/createorder" , method = RequestMethod.POST)
    public String showCars(@ModelAttribute Order order, Principal principal, Model model) {

//        System.out.println(order.toString());
        Client cl = clientService.getClient(principal.getName());
        Order o = orderService.getLastOrderByCl(cl);
        Car goodCar = carService.getCar(order.getCar().getCarId());

        if (!goodCar.getCarStatus().equals("free")) {
            model.addAttribute("error", "Машина уже используется");
            return "error";
        }

        if (o == null || o.getOrderStatus().getId() != 1) {
            order.setOrderStatus(orderService.getOrderStatus(1));
            order.setClient(clientService.getClient(principal.getName()));
            orderService.insertOrder(order);
            carService.updateStatus("in use",order.getCar().getCarId());
        }

        else {
            model.addAttribute("error", "У вас есть незаверешенный заказ");
            return "error";
        }


        return "redirect:/home/finishorder";
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


}