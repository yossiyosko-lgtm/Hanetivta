package com.restaurant.repository;

import com.restaurant.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Spring יוצר את כל ה-SQL אוטומטית - לא צריך לכתוב כלום
public interface OrderRepository extends JpaRepository<Order, Long> {

    // מחזיר רק הזמנות עם סטטוס מסוים (למשל WAITING או APPROVED)
    List<Order> findByStatus(String status);
}
