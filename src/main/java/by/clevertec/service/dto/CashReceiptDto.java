package by.clevertec.service.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Accessors(chain = true)
public class CashReceiptDto {
    private CashierDto cashierDto;
    private ShopDto shopDto;
    private List<CashReceiptItemDto> cashReceiptItemList;
    private DiscountCardDto discountCardDto;
    private LocalDate cashReceiptDate;
    private LocalTime cashReceiptTime;
    private Double totalDiscountPercent;
    private Double totalPrice;
}
