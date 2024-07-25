package ithillel.ua.glovo;// src/test/java/com/example/demo/service/OrderServiceTest.java

import ithillel.ua.glovo.model.Order;
import ithillel.ua.glovo.model.Product;
import ithillel.ua.glovo.repositories.OrderRepository;
import ithillel.ua.glovo.repositories.ProductRepository;
import ithillel.ua.glovo.service.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class OrderServiceTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderServiceImpl();
        orderService.orderRepository = orderRepository;
        orderService.productRepository = productRepository;
    }

    @Test
    void addOrder() {
        Order order = new Order();
        order.setStatus("NEW");
        Order savedOrder = orderService.addOrder(order);
        assertNotNull(savedOrder);
        assertEquals("NEW", savedOrder.getStatus());
    }

    @Test
    void getOrderById() {
        Order order = new Order();
        order.setStatus("NEW");
        Order savedOrder = orderRepository.save(order);
        Order foundOrder = orderService.getOrderById(savedOrder.getId());
        assertNotNull(foundOrder);
        assertEquals("NEW", foundOrder.getStatus());
    }

    @Test
    void updateOrder() {
        Order order = new Order();
        order.setStatus("NEW");
        Order savedOrder = orderRepository.save(order);
        savedOrder.setStatus("UPDATED");
        Order updatedOrder = orderService.updateOrder(savedOrder.getId(), savedOrder);
        assertNotNull(updatedOrder);
        assertEquals("UPDATED", updatedOrder.getStatus());
    }

    @Test
    void addProductToOrder() {
        Order order = new Order();
        order.setStatus("NEW");
        Order savedOrder = orderRepository.save(order);
        Product product = new Product();
        product.setName("Product 1");
        product.setPrice(10.0);
        Product savedProduct = productRepository.save(product);
        Order updatedOrder = orderService.addProductToOrder(savedOrder.getId(), savedProduct);
        assertNotNull(updatedOrder);
        assertEquals(1, updatedOrder.getProducts().size());
    }

    @Test
    void removeProductFromOrder() {
        Order order = new Order();
        order.setStatus("NEW");
        Product product = new Product();
        product.setName("Product 1");
        product.setPrice(10.0);
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        order.setProducts(products);
        Order savedOrder = orderRepository.save(order);
        Order updatedOrder = orderService.removeProductFromOrder(savedOrder.getId(), product.getId());
        assertNotNull(updatedOrder);
        assertEquals(0, updatedOrder.getProducts().size());
    }

    @Test
    void deleteOrder() {
        Order order = new Order();
        order.setStatus("NEW");
        Order savedOrder = orderRepository.save(order);
        boolean isDeleted = orderService.deleteOrder(savedOrder.getId());
        assertTrue(isDeleted);
        assertFalse(orderRepository.existsById(savedOrder.getId()));
    }
}