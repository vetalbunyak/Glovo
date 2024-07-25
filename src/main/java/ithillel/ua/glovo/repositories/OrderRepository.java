package ithillel.ua.glovo.repositories;

import ithillel.ua.glovo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}