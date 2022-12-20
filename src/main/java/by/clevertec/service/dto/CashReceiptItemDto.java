package by.clevertec.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class CashReceiptItemDto {
    private String productName;
    private Long productQuantity;
    private Double productPrice;
    private Double productTotalPrice;
}
