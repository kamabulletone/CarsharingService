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

/**
 * Класс работы с БД машин.
 */
public class CarService {

    /**
     * Объект, реализующий методы репозитория категорий.
     */
    private CarRepository reps;

    /**
     * Конструктор CarService
     * @param reps репозиторий машин
     */
    public CarService(CarRepository reps) {
        this.reps = reps;
    }

    /**
     * Метод получения списка всех машин
     * @return список машин
     */
    public List<Car> getCars() {
        log.info("Find all items");
        return reps.findAll();
    }

    /**
     * Метод получения машины по id
     * @param id идентификатор машины
     * @return объект машины
     */
    public Car getCar(int id) {

        return reps.findById(id).get();
    }

    /**
     * Метод обновления статуса машины
     * @param status значение статуса
     * @param id идентификатор машины
     */
    public void updateStatus(String status, int id) {
        reps.changeCarStatus(status,id);
    }

    /**
     * Метод получения списка машин без заказов
     * @return список машин
     */
    public List<Car> getCarsWithNoOrders() {
        return reps.getCarsWithNoOrders();
    }

    /**
     * Метод добавления машины
     * @param a Объект машины для вставки
     */
    public void insertCar(Car a) {
        log.info("Save item {}", a);
        reps.save(a);
    }

    /**
     * Метод удаления машины
     * @param a Объект машины для удаления
     */
    public void deleteCar(Car a) {
        log.info("Delete item {}", a);
        reps.delete(a);
    }

    /**
     * Метод удаления машины по id
     * @param id идентификатор машины
     */
    public void deleteById(int id) {
        reps.delete(getCar(id));
    }
}
