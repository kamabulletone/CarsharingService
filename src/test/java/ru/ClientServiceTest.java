package ru;


import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.model.Client;
import ru.model.Order;
import ru.repositories.CarRepository;
import ru.repositories.ClientRepository;
import ru.services.CarService;
import ru.services.ClientService;

import java.util.*;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ClientServiceTest {

    @InjectMocks
    ClientService clientService;

    @Mock
    ClientRepository clientRepository;

    @Mock
    BCryptPasswordEncoder encoder;

    @Test
    public void getClientTest() {
        String email = "1email";

        Client cl1 = new Client();
        cl1.setEmail("1email");

        Client cl2 = new Client();
        cl2.setEmail("2email");

        Mockito.when(clientRepository.findByEmail(email)).thenReturn(cl1);
        Mockito.when(clientRepository.findByEmail("ada")).thenReturn(null);

        Client res1 = clientService.getClient(email);
        Client res2 = clientService.getClient("ada");

        Assertions.assertEquals(cl1.getEmail(),res1.getEmail());
        Assertions.assertNull(res2);
    }

    @Test
    public void getClientsWithNoOrdersTest() {

        Client cl1 = new Client();
        cl1.setEmail("1email");
        cl1.setClientID(1);

        Client cl2 = new Client();
        cl2.setEmail("2email");
        cl2.setClientID(2);

        Client cl3 = new Client();
        cl3.setEmail("3email");
        cl3.setClientID(3);

        Order o1 = new Order();
        o1.setOrderId(1);
        o1.setClient(cl2);

        Order o2 = new Order();
        o2.setOrderId(2);
        o2.setClient(cl2);

        List<Order> orders = new ArrayList<Order>();
        orders.add(o1);
        orders.add(o2);

        Mockito.when(clientRepository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(cl1,cl2,cl3)));

        List<Client> clients = clientService.getClients();


        orders.forEach(order -> {
                if (order.getClient() != null) {
                    clients.remove(order.getClient());
                }
        }) ;

        Mockito.when(clientRepository.getClientsWithNoOrders()).thenReturn(clients);

        List<Client> res = clientService.getClientsWithNoOrders();

        Assertions.assertEquals(Arrays.asList(cl1,cl3), res);




    }
}
