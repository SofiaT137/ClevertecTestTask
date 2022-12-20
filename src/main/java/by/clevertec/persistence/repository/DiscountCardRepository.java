package by.clevertec.persistence.repository;

import by.clevertec.persistence.entity.DiscountCard;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The "DiscountCardRepository" interface extends JpaRepository functionality for the "DiscountCard" entity
 */
public interface DiscountCardRepository extends JpaRepository<DiscountCard, Long> {
}
