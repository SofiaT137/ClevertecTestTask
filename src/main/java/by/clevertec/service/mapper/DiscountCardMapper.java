package by.clevertec.service.mapper;

import by.clevertec.persistence.entity.DiscountCard;
import by.clevertec.service.dto.DiscountCardDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface DiscountCardMapper {
    @Mapping(target = "id", ignore = true)
    DiscountCard mapToDiscountCard(DiscountCardDto discountCardDto);
    DiscountCardDto mapToDiscountCardDto(DiscountCard discountCard);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateDiscountCardFromDiscountCardDto(DiscountCardDto discountCardDto,
                                               @MappingTarget DiscountCard discountCard);
}
