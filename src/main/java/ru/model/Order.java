package ru.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.mapping.Join;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "orders", uniqueConstraints={@UniqueConstraint(columnNames ={"order_id", "client_id_FK",
        "car_id_FK","order_status_FK", "created_on"})})
/**
 * Класс, реализующий сущность заказа
 */
public class Order {

    /**
     * Идентификатор заказа
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", updatable = false, nullable = false)
    private int orderId;

    /**
     * Объект клиента, со связью один ко одному
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "client_id_FK", referencedColumnName = "client_id")
    private Client client;

    /**
     * Объект машины, со связью один ко одному
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    //@JsonManagedReference
    @JoinColumn(name = "car_id_FK", referencedColumnName = "car_id")
    private Car car;

    /**
     * Объект cтатуса заказа, со связью один ко одному
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "order_status_FK", referencedColumnName = "id")
    private OrderStatus orderStatus;

    /**
     * Дата создания закза
     */
    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;

    /**
     * Метод определния текущей даты
     */
    @PrePersist
    public void prePersist() {
        createdOn = LocalDateTime.now();

    }













}
