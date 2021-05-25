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

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@AllArgsConstructor
public class MyController {



    @Autowired
    private CarService carService;

    @Autowired
    private OrderService orderService;



    @Autowired
    private ClientService clientService;

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



        clientService.signUpUser(client);

        return "redirect:/login";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    String showhome(Model model, Principal principal) {

        String role = clientService.getClient(principal.getName()).getAuthorities().size() == 1 ? "ROLE_USER" : "ROLE_ADMIN";
        model.addAttribute("role", role);
        model.addAttribute("clientName",principal.getName());
        System.out.println(clientService.getClient(principal.getName()).getAuthorities() + " " + role + "");

        return "home";
    }




    @RequestMapping(value = "/home/finishorder", method = RequestMethod.GET)
    public String finishOrderV(Model model, Principal principal) {

        Client cl = clientService.getClient(principal.getName());
        Order o = orderService.getLastOrderByCl(cl);

        if (o == null || o.getOrderStatus().getDescription().equals("finished")) {
            model.addAttribute("error", "Нет текущих заказов");
            return "error";
        }

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

            carService.deleteById(carid);
        }
        return "redirect:/home/delete";

    }

    @RequestMapping(value = "/home/deleteclient", method = RequestMethod.POST)
    public String deleteClient(@RequestParam(value = "clients") int[] clientsId) {
        for (int clientId:clientsId
        ) {

            clientService.deleteById(clientId);
        }
        return "redirect:/home/delete";

    }

    @RequestMapping(value = "/home/delete", method = RequestMethod.GET)
    public String getDeleteV(Model model) {


        model.addAttribute("clients", clientService.getClientsWithNoOrders());

        model.addAttribute("cars", carService.getCarsWithNoOrders());


        return "delete";

    }

    @RequestMapping(value = "/home/createorder" , method = RequestMethod.GET)
    public String showCars(Model model, Principal principal) {
        model.addAttribute("order", new Order());
        model.addAttribute("cars", carService.getCars());

        model.addAttribute("clientName",principal.getName());
        Client client = clientService.getClient(principal.getName());


        return "choose";
    }


    @RequestMapping(value = "/home/createorder" , method = RequestMethod.POST)
    public String showCars(@ModelAttribute Order order, Principal principal, Model model) {

        Client cl = clientService.getClient(principal.getName());
        Order o = orderService.getLastOrderByCl(cl);

        Car goodCar = carService.getCar(order.getCar().getCarId());

        if (!goodCar.getCarStatus().equals("free")) {
            model.addAttribute("error", "Машина уже используется");
            model.addAttribute("order", new Order());
            model.addAttribute("cars", carService.getCars());
            model.addAttribute("clientName",principal.getName());
            return "choose";
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

    @RequestMapping(value = "/home/createcar" , method = RequestMethod.GET)
    public String showCars(Model model) {

        model.addAttribute("car", new Car());

        return "addcar";
    }

    @RequestMapping(value = "/home/createcar" , method = RequestMethod.POST)
    public String showCars(@ModelAttribute Car car, Model model) {

        List<Car> cars = carService.getCars();


        if (car.getCarMark().equals("") || car.getCarRegistrationNumber().equals("") || car.getCarModel().equals("")) {
            model.addAttribute("error", "Не все поля заполнены");
            return "addcar";
        }

        if (cars.stream().anyMatch(c -> c.getCarRegistrationNumber().equals(car.getCarRegistrationNumber()))) {
            model.addAttribute("error", "Машина с таким регистрационном номером уже существует");
            model.addAttribute("car", new Car());
            return "addcar";
        }

        car.setCarStatus("free");
        carService.insertCar(car);

        model.addAttribute("success", "Машина успешна добавлена.");

        return "addcar";
    }


}