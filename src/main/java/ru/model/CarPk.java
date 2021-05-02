package ru.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class CarPk implements Serializable {
    protected int carId;
    protected String carRegistrationNumber;

}
