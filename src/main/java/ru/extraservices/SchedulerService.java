package ru.extraservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.services.CarService;
import ru.services.OrderService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


@ManagedResource
@Service
public class SchedulerService  {
    private static String itemFIle = "directory\\item.txt";
    private static String orderFile = "directory\\order.txt";
    @Autowired
    private CarService ir;

    @Autowired
    private OrderService or;


    @Scheduled(cron = "0 */30 * * * *")
    @ManagedOperation
    public void updateItemFile() {

        for (File myFile : new File("directory").listFiles()) {
            if (myFile.isFile()) myFile.delete();
        }

        try {
            File item = new File(itemFIle);
            File order = new File(orderFile);
            item.createNewFile();
            order.createNewFile();
            FileWriter fw1 = new FileWriter(item);
            FileWriter fw2 = new FileWriter(order);
            fw1.write(ir.getItems().toString());
            fw2.write(or.getOrders().toString());
            fw1.flush();
            fw2.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
