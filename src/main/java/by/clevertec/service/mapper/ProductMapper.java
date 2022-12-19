package by.clevertec.service.mapper;

import by.clevertec.persistence.entity.Product;
import by.clevertec.service.dto.ProductDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product mapToProduct(ProductDto productDto);
    ProductDto mapToProductDto(Product product);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProductFromProductDto(ProductDto productDto,
                                               @MappingTarget Product product);
}
