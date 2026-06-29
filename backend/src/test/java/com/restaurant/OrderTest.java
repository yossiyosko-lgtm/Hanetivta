package com.restaurant;

import com.restaurant.model.Order;
import com.restaurant.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void createOrder_statusShouldBeWaiting() {
        // יצירת הזמנה חדשה
        Order order = new Order("5", "שניצל + קולה");
        orderRepository.save(order);

        // וידוא שהסטטוס הוא WAITING
        assertEquals("WAITING", order.getStatus());
    }

    @Test
    void approveOrder_statusShouldBeApproved() {
        // יצירת הזמנה ואישור שלה
        Order order = new Order("3", "פיצה");
        orderRepository.save(order);

        order.setStatus("APPROVED");
        orderRepository.save(order);

        // וידוא שהסטטוס עודכן
        Order saved = orderRepository.findById(order.getId()).orElseThrow();
        assertEquals("APPROVED", saved.getStatus());
    }
}
