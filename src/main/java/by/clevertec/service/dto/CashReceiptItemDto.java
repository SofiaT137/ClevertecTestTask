package by.clevertec.service.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CashReceiptItemDto {
    private final String productName;
    private final Long productQuantity;
    private final Double productPrice;
    private final Double productTotalPrice;
}
