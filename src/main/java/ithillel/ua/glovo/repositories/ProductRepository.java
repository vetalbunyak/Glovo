package ithillel.ua.glovo.repositories;

import ithillel.ua.glovo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}