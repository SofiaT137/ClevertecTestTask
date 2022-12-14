package by.clevertec.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Table(name = "discount_card")
public class DiscountCard extends AbstractEntity<Long> {

    @Column(name = "card_discount_percent", nullable = false)
    private Integer cardDiscountPercent;
}
