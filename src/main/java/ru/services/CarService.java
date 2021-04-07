package ru.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.model.Car;
import ru.repositories.CarRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class CarService {

    private CarRepository reps;

    public CarService(CarRepository reps) {
        this.reps = reps;
    }

    public List<Car> getItems() {
        log.info("Find all items");
        return reps.findAll();
    }

    public List<Car> orderByName() {
        log.info("Find all items order by name");
        return reps.findAll(Sort.by("name"));
    }

    public List<Car> orderByDate() {
        log.info("Find all items order by creationDate");
        return reps.findAll(Sort.by("creationDate"));
    }

    public List<Car> orderByPrice() {
        log.info("Find all items order by price");
        return reps.findAll(Sort.by("price"));
    }

    public void insertItem(Car a) {
        log.info("Save item {}", a);
        reps.save(a);
    }

    public void deleteItem(Car a) {
        log.info("Delete item {}", a);
        reps.delete(a);
    }
}
