package com.restaurant.controller;

import com.restaurant.model.Order;
import com.restaurant.repository.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*") // מאפשר לדפדפן לשלוח בקשות
public class OrderController {

    private final OrderRepository orderRepository;

    // Spring מזריק את ה-repository אוטומטית
    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // GET /api/orders - מחזיר את כל ההזמנות
    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // GET /api/orders/waiting - מחזיר רק הזמנות שממתינות (למסך מטבח)
    @GetMapping("/waiting")
    public List<Order> getWaitingOrders() {
        return orderRepository.findByStatus("WAITING");
    }

    // GET /api/orders/approved - מחזיר רק הזמנות שאושרו (למסך לקוח)
    @GetMapping("/approved")
    public List<Order> getApprovedOrders() {
        return orderRepository.findByStatus("APPROVED");
    }

    // POST /api/orders - יוצר הזמנה חדשה
    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderRepository.save(order);
    }

    // PUT /api/orders/{id}/approve - מאשר הזמנה לפי ID
    @PutMapping("/{id}/approve")
    public ResponseEntity<Order> approveOrder(@PathVariable Long id) {
        return orderRepository.findById(id)
                .map(order -> {
                    order.setStatus("APPROVED");
                    return ResponseEntity.ok(orderRepository.save(order));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
