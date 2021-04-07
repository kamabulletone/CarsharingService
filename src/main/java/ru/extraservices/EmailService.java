package ru.extraservices;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.model.Car;
import ru.model.Order;

import javax.transaction.Transactional;


@Service
@Transactional
public class EmailService {



    @Autowired
    private JavaMailSender javaMailSender;


    @Async
    public void sendEmailItem(Car w) {

        SimpleMailMessage message = new SimpleMailMessage();
      //  message.setFrom("kamacringe@yandex.ru");
        message.setTo("okostya2002@yandex.ru");
        message.setSubject("ItemCreation");
        message.setText("Item created " + w.toString());
        javaMailSender.send(message);


    }

    @Async
    public void sendEmailOrder(Order w) {

        SimpleMailMessage message = new SimpleMailMessage();
        //  message.setFrom("kamacringe@yandex.ru");
        message.setTo("okostya2002@yandex.ru");
        message.setSubject("OrderCreation");
        message.setText("Order created " + w.toString());
        javaMailSender.send(message);


    }



}
