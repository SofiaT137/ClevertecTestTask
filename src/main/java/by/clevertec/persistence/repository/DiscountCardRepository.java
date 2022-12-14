package by.clevertec.persistence.repository;

import by.clevertec.persistence.entity.DiscountCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountCardRepository extends JpaRepository<DiscountCard, Long> {
}
