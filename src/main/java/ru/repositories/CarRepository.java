package ru.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.model.Car;
import ru.model.Client;

import java.util.List;


/**
 * Интерфейс реализующий работу с репозиторием машины.
 * А именно: изменение статуса машины по id машины и id заказа,
 * получение списка машин без заказаов.
 */
public interface CarRepository extends JpaRepository<Car, Integer>, JpaSpecificationExecutor<Car> {

    @Modifying
    @Query("update Car c set c.carStatus = ?1 where c.carId = ?2")
    int changeCarStatus(String status, Integer id);

    @Query("from Car c where c.carId not in (Select car from Order o)")
    List<Car> getCarsWithNoOrders();
}
