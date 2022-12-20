package by.clevertec.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Class "CashReceiptDto" helps to create the "CashReceiptDto" entity
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class CashReceiptDto {
    private static final String DATE_REGEX = "yyyy/MM/dd";
    private static final String TIME_REGEX = "HH:mm:ss";

    private CashierDto cashierDto;
    private ShopDto shopDto;
    private List<CashReceiptItemDto> cashReceiptItemList;
    private DiscountCardDto discountCardDto;
    @JsonFormat(pattern = DATE_REGEX)
    private LocalDate cashReceiptDate;
    @JsonFormat(pattern = TIME_REGEX)
    private LocalTime cashReceiptTime;
    private Double totalDiscount;
    private Double totalPrice;
}
