package ithillel.ua.glovo.service;

import ithillel.ua.glovo.model.Order;
import ithillel.ua.glovo.model.Product;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    private Map<Long, Order> orders = new HashMap<>();
    private Long nextId = 1L;
    @Override
    public Order getOrderById(Long id) {
        return orders.get(id);
    }

    @Override
    public Order addOrder(Order order) {
       order.setId(nextId++);
       orders.put(order.getId(), order);
       return order;
    }

    @Override
    public Order updateOrder(Long id, Order order) {
        order.setId(id);
        orders.put(id,order);
        return order;
    }

    @Override
    public Order addProductToOrder(Long orderId, Product product) {
        Order order = orders.get(orderId);
        if (order != null) {
            order.getProducts().add(product);
        }
        return order;
    }

    @Override
    public Order removeProductFromOrder(Long orderId, Long productId) {
        Order order = orders.get(orderId);
        if (order != null) {
            order.getProducts().removeIf(product -> product.getId().equals(productId));
        }
        return  order;
    }

    @Override
    public boolean deleteOrder(Long id) {
        return  orders.remove(id) != null;
    }
}
