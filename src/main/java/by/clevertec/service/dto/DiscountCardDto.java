package by.clevertec.service.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DiscountCardDto {
    private final Integer cardDiscountPercent;
}
