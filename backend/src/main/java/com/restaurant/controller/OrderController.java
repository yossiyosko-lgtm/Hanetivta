package com.restaurant.controller;

import com.restaurant.model.Order;
import com.restaurant.repository.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/waiting")
    public List<Order> getWaitingOrders() {
        return orderRepository.findByStatus("WAITING");
    }

    @GetMapping("/approved")
    public List<Order> getApprovedOrders() {
        return orderRepository.findByStatus("APPROVED");
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderRepository.save(order);
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<Order> approveOrder(@PathVariable Long id) {
        return orderRepository.findById(id)
                .map(order -> {
                    order.setStatus("APPROVED");
                    return ResponseEntity.ok(orderRepository.save(order));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ חדש - מחיקת הזמנה לפי ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        return orderRepository.findById(id)
                .map(order -> {
                    orderRepository.delete(order);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
