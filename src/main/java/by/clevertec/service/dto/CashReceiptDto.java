package by.clevertec.service.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
public class CashReceiptDto {

    private Integer cashierId;
    private Map<ProductDto, Integer> productDtoMap;
    private List<DiscountCardDto> discountCardDtoList;
    private LocalDateTime cashReceiptDate;
}
