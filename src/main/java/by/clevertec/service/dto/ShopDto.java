package by.clevertec.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ShopDto {
    private String shopName;
    private String shopAddress;
    private String shopPhone;
}
