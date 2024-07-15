package ithillel.ua.glovo;// src/test/java/com/example/demo/service/OrderServiceTest.java

import ithillel.ua.glovo.model.Order;
import ithillel.ua.glovo.model.Product;
import ithillel.ua.glovo.service.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderServiceImpl();
    }

    @Test
    void testAddOrder() {
        Order order = new Order();
        order.setProducts(new ArrayList<>());
        order.setStatus("new");

        Order addedOrder = orderService.addOrder(order);

        assertNotNull(addedOrder.getId());
        assertEquals("new", addedOrder.getStatus());
    }

    @Test
    void testGetOrderById() {
        Order order = new Order();
        order.setProducts(new ArrayList<>());
        order.setStatus("new");

        Order addedOrder = orderService.addOrder(order);

        Order fetchedOrder = orderService.getOrderById(addedOrder.getId());

        assertNotNull(fetchedOrder);
        assertEquals(addedOrder.getId(), fetchedOrder.getId());
    }

    @Test
    void testUpdateOrder() {
        Order order = new Order();
        order.setProducts(new ArrayList<>());
        order.setStatus("new");

        Order addedOrder = orderService.addOrder(order);

        addedOrder.setStatus("completed");
        Order updatedOrder = orderService.updateOrder(addedOrder.getId(), addedOrder);

        assertEquals("completed", updatedOrder.getStatus());
    }

    @Test
    void testAddProductToOrder() {
        Order order = new Order();
        order.setProducts(new ArrayList<>());
        order.setStatus("new");

        Order addedOrder = orderService.addOrder(order);

        Product product = new Product();
        product.setId(1L);
        product.setName("Product 1");
        product.setPrice(10.0);

        Order updatedOrder = orderService.addProductToOrder(addedOrder.getId(), product);

        assertFalse(updatedOrder.getProducts().isEmpty());
        assertEquals("Product 1", updatedOrder.getProducts().get(0).getName());
    }

    @Test
    void testRemoveProductFromOrder() {
        Order order = new Order();
        order.setProducts(new ArrayList<>());
        order.setStatus("new");

        Product product = new Product();
        product.setId(1L);
        product.setName("Product 1");
        product.setPrice(10.0);

        order.getProducts().add(product);

        Order addedOrder = orderService.addOrder(order);

        Order updatedOrder = orderService.removeProductFromOrder(addedOrder.getId(), product.getId());

        assertTrue(updatedOrder.getProducts().isEmpty());
    }

    @Test
    void testDeleteOrder() {
        Order order = new Order();
        order.setProducts(new ArrayList<>());
        order.setStatus("new");

        Order addedOrder = orderService.addOrder(order);

        boolean deleted = orderService.deleteOrder(addedOrder.getId());

        assertTrue(deleted);
        assertNull(orderService.getOrderById(addedOrder.getId()));
    }
}