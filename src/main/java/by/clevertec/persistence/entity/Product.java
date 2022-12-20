package by.clevertec.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * The "Product" class extends "AbstractEntity" class and presents creation of the "Product" entity
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Table(name = "product")
public class Product extends AbstractEntity<Long> {

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_price", nullable = false)
    private Double price;

    @Column(name = "product_discount_percent",nullable = false)
    private Integer productDiscountPercent;
}
