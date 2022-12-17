package by.clevertec.service.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ShopDto {
    private final String shopName;
    private final String shopAddress;
    private final String shopPhone;
}
