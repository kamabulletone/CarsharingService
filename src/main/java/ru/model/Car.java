package ru.model;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@Table(name = "Cars", schema = "carsharing")
//@IdClass(CarPk.class)
public class Car implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id", updatable = false, nullable = false)
    private int carId;

    //@Id
    @Column(name = "car_registr_num", unique = true, updatable = false)
    private String carRegistrationNumber;

    @Column(name = "car_mark")
    private String carMark;

    @Column(name = "car_model")
    private String carModel;

    @Column(name = "car_status")
    private String carStatus;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "car")
    private Order order;

//    @Fetch(FetchMode.JOIN)
//    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
//    private List<Order> orders;



}
