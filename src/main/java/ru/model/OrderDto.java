package ru.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;


@Data
@NoArgsConstructor

public class OrderDto {
    @JsonIgnore
    int car_id;
    int client_id;
    @JsonIgnore
    int order_status_id;

    Car car;
    OrderStatus orderStatus;
    Client client;


    String carMark;

    @JsonIgnore
    String carModel;
    @JsonIgnore
    String carStatus;

    @JsonIgnore
    String order_status;

    public OrderDto(int car_id, int client_id, int order_status_id) {
        this.car_id = car_id;
        this.client_id = client_id;
        this.order_status_id = order_status_id;
    }
}
