package ru.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.model.Car;


public interface CarRepository extends JpaRepository<Car, String> {
}
