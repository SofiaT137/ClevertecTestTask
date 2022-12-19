package by.clevertec.service.mapper;

import by.clevertec.persistence.entity.DiscountCard;
import by.clevertec.service.dto.DiscountCardDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface DiscountCardMapper {
    DiscountCard mapToDiscountCard(DiscountCardDto discountCardDto);
    DiscountCardDto mapToDiscountCardDto(DiscountCard discountCard);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDiscountCardFromDiscountCardDto(DiscountCardDto discountCardDto,
                                               @MappingTarget DiscountCard discountCard);
}
