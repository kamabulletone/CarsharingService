package ru.model;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Parent;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Validated
@Table(name = "cars")
/**
 * Класс, реализующий сущность машины
 */
public class Car{

    /**
     * Идентификатор машины
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id", updatable = false, nullable = false)
    private int carId;

    /**
     * Регистрационный номер машины
     */
    @Column(name = "car_registr_num", unique = true, updatable = false)
    private String carRegistrationNumber;

    /**
     * Марка машины
     */
    @Column(name = "car_mark")
    private String carMark;

    /**
     * Модель машины
     */
    @Column(name = "car_model")
    private String carModel;

    /**
     * Статус машины
     */
    @Column(name = "car_status")
    private String carStatus;

}
