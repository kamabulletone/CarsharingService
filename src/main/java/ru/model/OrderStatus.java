package ru.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="order_status", schema = "carsharing")
@JsonIgnoreProperties(ignoreUnknown = true,
        value = {"hibernateLazyInitializer", "handler", "created"})
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="description", unique = true)
    private String description;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "orderStatus")
    @JsonBackReference
    private Order order;




}
