package by.clevertec.persistence.repository;

import by.clevertec.persistence.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The "ProductRepository" interface extends JpaRepository functionality for the "Product" entity
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
}
