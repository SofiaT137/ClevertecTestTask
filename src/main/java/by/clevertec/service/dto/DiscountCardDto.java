package by.clevertec.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Class "DiscountCardDto" helps to create the "DiscountCardDto" entity
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class DiscountCardDto {
    private Long id;
    private Integer cardDiscountPercent;
}
