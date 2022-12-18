package by.clevertec.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Accessors(chain = true)
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
