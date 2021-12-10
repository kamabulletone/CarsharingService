package ru.controller;



import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.Filters.CarFilter;
import ru.model.*;
import ru.services.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс, обрабатывающий операции на веб-ресурсе
 */
@Controller
@AllArgsConstructor
public class MyController {


    /**
     * Сервис, рабатающий с сущностью машин
     */
    @Autowired
    private CarService carService;

    /**
     * Сервис, работающий с сущностью заказов
     */
    @Autowired
    private OrderService orderService;

    /**
     * Сервис, работающий с сущностью клиентов
     */
    @Autowired
    private ClientService clientService;

    /**
     * Метод вывода страницы авторизации
     * @return страница для авторизации пользователя
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    /**
     * Метод возвращающий страницу регистрации
     * @param model Объект для записи информации на страницу веб-ресурса
     * @return страница регистрации
     */
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("client", new Client());
        return "registration";
    }

    /**
     * Метод, регистрирующий пользователя и перенаправляющий на странцу авторизации
     * @param client Объект клиента, связанный с контекстой переменной thymeleaf
     * @return страница авторизации
     */
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    String signUp(@ModelAttribute Client client) {
        client.setDriverLicense("none");
        client.setFacePhoto("none");



        clientService.signUpUser(client);

        return "redirect:/login";
    }

    /**
     * Метод, возвращающий домашнюю страницу для юзера и другую для админа
     * @param model Объект для записи информации на страницу веб-ресурса
     * @param principal Объект, содержащий информацию о залогиненном пользователе
     * @return домашняя страница
     */
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    String showhome(Model model, Principal principal) {

        String role = clientService.getClient(principal.getName()).getAuthorities().size() == 1 ? "ROLE_USER" : "ROLE_ADMIN";
        model.addAttribute("role", role);
        model.addAttribute("clientName",principal.getName());
        System.out.println(clientService.getClient(principal.getName()).getAuthorities() + " " + role + "");

        return "home";
    }


    /**
     * Метод, возвращающий страницу завершения заказа, на странице идет подсчет цены заказа
     * В случае того, если заказов нет или все в завершены, то вернуть страницу ошибки
     * @param model Объект для записи информации на страницу веб-ресурса
     * @param principal Объект, содержащий информацию о залогиненном пользователе
     * @return страница завершения заказа или ошибка
     */
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

    /**
     * Метод, заверщающий заказ(переводит в состояние "finished") и переводящий использованную машину в состояние "free"
     * @param cost сгенерированная цена заказа
     * @param principal Объект, содержащий информацию о залогиненном пользователе
     * @return домашняя страница
     */
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

    /**
     * Метод, удаляющий машину/ы, доступно только у админа
     * @param carsId Массив id машин
     * @return страница удаления
     */
    @RequestMapping(value = "/home/deletecar", method = RequestMethod.POST)
    public String deleteCar(@RequestParam(value = "cars") int[] carsId) {
        for (int carid:carsId
        ) {

            carService.deleteById(carid);
        }
        return "redirect:/home/delete";

    }

    /**
     * Метод, удаляющий клиента/ов, доступно только у админа
     * @param clientsId массив id клиентов
     * @return страница удаления
     */
    @RequestMapping(value = "/home/deleteclient", method = RequestMethod.POST)
    public String deleteClient(@RequestParam(value = "clients") int[] clientsId) {
        for (int clientId:clientsId
        ) {

            clientService.deleteById(clientId);
        }
        return "redirect:/home/delete";

    }

    /**
     * Метод, возвращающий страницу с клиентами и машинами без заказов
     * @param model Объект для записи информации на страницу веб-ресурса
     * @return страница удаления
     */
    @RequestMapping(value = "/home/delete", method = RequestMethod.GET)
    public String getDeleteV(Model model) {


        model.addAttribute("clients", clientService.getClientsWithNoOrders());

        model.addAttribute("cars", carService.getCarsWithNoOrders());


        return "delete";

    }

    /**
     * Метод, возвращающий страницу для создания заказа
     * @param model Объект для записи информации на страницу веб-ресурса
     * @param principal Объект, содержащий информацию о залогиненном пользователе
     * @return страница создания заказа
     */
    @RequestMapping(value = "/home/createorder" , method = RequestMethod.GET)
    public String showCars(Model model, Principal principal, CarFilter filter,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Car> cars = carService.getCars(filter,pageable);

        System.out.println(cars.getContent());
        System.out.println(page);

        model.addAttribute("order", new Order());
        model.addAttribute("cars", cars.getContent());
        model.addAttribute("page", page);
        model.addAttribute("totalPages", cars.getTotalPages());
        model.addAttribute("pageSize", size);

        model.addAttribute("clientName",principal.getName());
        Client client = clientService.getClient(principal.getName());


        return "choose";
    }

    /**
     * Методу, создающий заказ со статусом in process и переводит машину в статус in use
     * В методе проверяется используется ли уже выбранная машина
     * Также есть ли у клиента исптория заказов и/или незавершенные заказы
     * @param order Объект заказа, связанный с контекстной переменной thymeleaf
     * @param principal Объект, содержащий информацию о залогиненном пользователе
     * @param model Объект для записи информации на страницу веб-ресурса
     * @return страница завершения заказа или страница создания заказа или ошибка
     */
    @RequestMapping(value = "/home/createorder" , method = RequestMethod.POST)
    public String showCars(@ModelAttribute Order order, Principal principal, Model model,CarFilter filter,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "5") int size) {

        Client cl = clientService.getClient(principal.getName());
        Order o = orderService.getLastOrderByCl(cl);

        Car goodCar = carService.getCar(order.getCar().getCarId());

        Pageable pageable = PageRequest.of(page, size);
        Page<Car> cars = carService.getCars(filter,pageable);

        if (!goodCar.getCarStatus().equals("free")) {
            model.addAttribute("error", "Машина уже используется");
            model.addAttribute("order", new Order());
            model.addAttribute("cars", cars.getContent());
            model.addAttribute("page", page);
            model.addAttribute("pageSize", size);
            model.addAttribute("totalPages", cars.getTotalPages());
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

    /**
     * Метод, возвращающий странцу создания машины, только для админа
     * @param model Объект для записи информации на страницу веб-ресурса
     * @return страница создания машины
     */
    @RequestMapping(value = "/home/createcar" , method = RequestMethod.GET)
    public String showCars(Model model) {

        model.addAttribute("car", new Car());

        return "addcar";
    }

    /**
     * Метод, создающий машину со статусом free(свободна)
     * Проверятеся все ли поля заполнены, существует ли уже такая машина
     * @param car Объект машины, связанный с контекстной переменной thymeleaf
     * @param model Объект для записи информации на страницу веб-ресурса
     * @return странца создания машины
     */
    @RequestMapping(value = "/home/createcar" , method = RequestMethod.POST)
    public String showCars(@ModelAttribute Car car, Model model, CarFilter filter,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Car> carsPage = carService.getCars(filter,pageable);
        List<Car> cars = carsPage.getContent();


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