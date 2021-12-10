package ru;


import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import ru.Filters.CarFilter;
import ru.model.Car;
import ru.repositories.CarRepository;
import ru.services.CarService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssumptions.given;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CarServiceTest {

    @InjectMocks
    CarService carService;

    @Mock
    CarRepository carRepository;

    @Test
    public void getCarsTest() {

        Car car1 = new Car();
        car1.setCarId(1);
        car1.setCarMark("Mark1");

        Car car2 = new Car();
        car2.setCarId(2);
        car2.setCarMark("Mark2");

        List<Car> list = new ArrayList<Car>();

        list.add(car1);
        list.add(car2);

        Mockito.when(carRepository.findAll()).thenReturn(list);

        List<Car> cars = carService.getCars();

        Assertions.assertEquals(2,cars.size());
        Assertions.assertEquals(car1, cars.get(0));
        Assertions.assertEquals(car2, cars.get(1));
        Mockito.verify(carRepository, times(1)).findAll();


    }

    @Test
    public void getCar() {
        Car car1 = new Car();
        car1.setCarId(1);
        car1.setCarMark("Mark1");

        Car car2 = new Car();
        car2.setCarId(2);
        car2.setCarMark("Mark2");

        Optional<Car> o = Optional.of(car1);

        Mockito.when(carRepository.findById(1)).thenReturn(Optional.of(car1));
        Mockito.when(carRepository.findById(2)).thenReturn(Optional.of(car2));


        Car res1 = carService.getCar(1);
        Car res2 = carService.getCar(2);

        Assertions.assertEquals(car1,res1);
        Assertions.assertEquals(car1.getCarId(), 1);

        Assertions.assertEquals(car2,res2);
        Assertions.assertEquals(car2.getCarId(), 2);


    }




}
