package by.clevertec.service.mapper;

import by.clevertec.persistence.entity.DiscountCard;
import by.clevertec.service.dto.DiscountCardDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DiscountCardMapper {
    DiscountCard mapToDiscountCard(DiscountCardDto discountCardDto);
    DiscountCardDto mapToDiscountCardDto(DiscountCard discountCard);
}
