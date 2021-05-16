package ru.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.model.Car;


public interface CarRepository extends JpaRepository<Car, Integer> {

    @Modifying
    @Query("update Car c set c.carStatus = ?1 where c.carId = ?2")
    int changeCarStatus(String status, Integer id);
}
