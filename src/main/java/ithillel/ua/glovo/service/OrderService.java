package ithillel.ua.glovo.service;

import ithillel.ua.glovo.model.Order;
import ithillel.ua.glovo.model.Product;

public interface OrderService {
    Order addOrder(Order order);
    Order getOrderById(Long id);
    Order updateOrder(Long id, Order order);
    Order addProductToOrder(Long orderId, Product product);
    Order removeProductFromOrder(Long orderId, Long productId);
    boolean deleteOrder(Long id);
}
