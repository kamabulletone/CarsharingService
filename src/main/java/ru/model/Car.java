package ru.model;



import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "items")
public class Car {
    @Id
    @Column(name = "name")
    public String name;
    @Column(name = "creationdate")
    public String creationDate;
    @Column(name = "price")
    public int price;

    @Fetch(FetchMode.JOIN)
    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private List<Order> orders;

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Car() { }

    public Car(String name, String creationDate, int price) {
        this.name = name;
        this.creationDate = creationDate;
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", price=" + price +
                ", orders=" + orders.toString() +
                '}';
    }
}
