package ru;


import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import ru.model.Car;
import ru.services.CarService;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CarServiceTest {

    @Mock
    CarService carService;

    @Test
    public void getCars() {

        Car car1 = new Car();
        car1.setCarId(1);
        car1.setCarMark("Mark1");

        Car car2 = new Car();
        car2.setCarId(2);
        car2.setCarMark("Mark2");

        Mockito
                .when(carService.getCars())
                .thenReturn(List.of(car1, car2));

        List<Car> cars = carService.getCars();


        Assertions.assertEquals(2, cars.size());
        Assertions.assertEquals(car2, cars.get(1));
        Assertions.assertEquals(car1, cars.get(0));

    }

}
