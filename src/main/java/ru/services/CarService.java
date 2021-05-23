package ru.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.model.Car;
import ru.repositories.CarRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
public class CarService {

    private CarRepository reps;

    public CarService(CarRepository reps) {
        this.reps = reps;
    }

    public List<Car> getCars() {
        log.info("Find all items");
        return reps.findAll();
    }

    public Car getCar(int id) {
       // System.out.println(id.getClass().getName());
        return reps.findById(id).get();
    }


    public void updateStatus(String status, int id) {
        reps.changeCarStatus(status,id);
    }

    public List<Car> getCarsWithNoOrders() {
        return reps.getCarsWithNoOrders();
    }

    public void insertCar(Car a) {
        log.info("Save item {}", a);
        reps.save(a);
    }

    public void deleteCar(Car a) {
        log.info("Delete item {}", a);
        reps.delete(a);
    }

    public void deleteById(int id) {
        reps.delete(getCar(id));
    }
}
