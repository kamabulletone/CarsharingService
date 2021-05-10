package ru.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.mapping.Join;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "orders", schema = "carsharing", uniqueConstraints={@UniqueConstraint(columnNames ={"order_id", "client_id_FK",
        "car_id_FK","order_status_FK", "created_on"})})
//@JsonIgnoreProperties(ignoreUnknown = true,
//        value = {"hibernateLazyInitializer", "handler", "created"})

public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", updatable = false, nullable = false)
    private int orderId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "client_id_FK", referencedColumnName = "client_id")
    private Client client;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    //@JsonManagedReference
    @JoinColumn(name = "car_id_FK", referencedColumnName = "car_id")
    private Car car;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "order_status_FK", referencedColumnName = "id")
    private OrderStatus orderStatus;

    @Column(name = "created_on", nullable = false)
    private LocalDate createdOn;

    @PrePersist
    public void prePersist() {
        createdOn = LocalDate.now();

    }













}
