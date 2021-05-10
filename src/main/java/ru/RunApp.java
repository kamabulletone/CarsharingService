package ru;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.model.Car;
import ru.model.Client;


@SpringBootApplication
public class RunApp {
    public static void main(String[] args) {
        SpringApplication.run(RunApp.class, args);
        Client client = new Client();
        Client client1 = new Client();
        System.out.println(client.toString());
        System.out.println(client1.toString());

        Car clint1 = new Car();
        System.out.println(clint1.toString());


        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
