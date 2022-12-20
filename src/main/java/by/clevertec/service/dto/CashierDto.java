package by.clevertec.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Class "CashierDto" helps to create the "CashierDto" entity
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class CashierDto {
    private Long cashierId;
}
