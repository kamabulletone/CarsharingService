package ru.model;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "cars", schema = "carsharing")

public class Car{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id", updatable = false, nullable = false)
    private int carId;


    @Column(name = "car_registr_num", unique = true, updatable = false)
    private String carRegistrationNumber;

    @Column(name = "car_mark")
    private String carMark;

    @Column(name = "car_model")
    private String carModel;

    @Column(name = "car_status")
    private String carStatus;

}
