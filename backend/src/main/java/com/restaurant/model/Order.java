package com.restaurant.model;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tableNumber;
    private String items;

    // WAITING = ממתין, APPROVED = אושר
    private String status = "WAITING";

    // JPA צריך constructor ריק
    public Order() {}

    public Order(String tableNumber, String items) {
        this.tableNumber = tableNumber;
        this.items = items;
        this.status = "WAITING";
    }

    // Getters & Setters
    public Long getId() { return id; }

    public String getTableNumber() { return tableNumber; }
    public void setTableNumber(String tableNumber) { this.tableNumber = tableNumber; }

    public String getItems() { return items; }
    public void setItems(String items) { this.items = items; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
