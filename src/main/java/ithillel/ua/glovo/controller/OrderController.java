package ithillel.ua.glovo.controller;

import ithillel.ua.glovo.model.Order;
import ithillel.ua.glovo.model.Product;
import ithillel.ua.glovo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @PostMapping
    public ResponseEntity<Order> addOrder(@RequestBody Order order){
        Order newOrder = orderService.addOrder(order);
        return ResponseEntity.ok(newOrder);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        Order updateOrder = orderService.updateOrder(id,order);
        return  ResponseEntity.ok(updateOrder);
    }

    @PatchMapping("/{orderId}/products")
    public ResponseEntity<Order> addProductToOrder(@PathVariable Long orderId, @RequestBody Product product) {
        Order updateOrder = orderService.addProductToOrder(orderId,product);
        return ResponseEntity.ok(updateOrder);
    }
    @DeleteMapping("/{orderId}/products/{productId}")
    public ResponseEntity<Order> removeProductFromOrder(@PathVariable Long orderId, @PathVariable Long productId) {
        Order updatedOrder = orderService.removeProductFromOrder(orderId, productId);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        boolean deleted = orderService.deleteOrder(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}

