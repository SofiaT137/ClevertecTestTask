package by.clevertec.service.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProductDto {
    private String productName;
    private Double price;
    private Integer productDiscountPercent;
}
