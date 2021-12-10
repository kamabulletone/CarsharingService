package ru.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.Filters.CarFilter;
import ru.model.Car;
import ru.repositories.CarRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public Page<Car> getCars(CarFilter filter, Pageable pageable) {
        log.info("Find all items with filter");
        return reps.findAll(byFilter(filter), pageable);
    }

    public List<Car> getCars() {
        log.info("Find all items with no filter");
        return reps.findAll();
    }


    private Specification<Car> byFilter(CarFilter filter) {
        return new Specification<Car>() {
            @Override
            public Predicate toPredicate(Root<Car> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if (filter.getCarMark() != null && !Objects.equals(filter.getCarMark(), "")) {
                    predicates.add(criteriaBuilder.equal(
                            root.get("carMark"), filter.getCarMark()
                    ));
                }
                if (filter.getCarModel() != null && !Objects.equals(filter.getCarModel(), "")) {
                    predicates.add(criteriaBuilder.equal(
                            root.get("carModel"), filter.getCarModel()
                    ));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
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
