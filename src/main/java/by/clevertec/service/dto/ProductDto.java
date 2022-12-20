package by.clevertec.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Class "ProductDto" helps to create the "ProductDto" entity
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ProductDto {
    private String productName;
    private Double price;
    private Integer productDiscountPercent;
}
