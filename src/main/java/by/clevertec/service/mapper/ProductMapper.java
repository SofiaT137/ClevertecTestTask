package by.clevertec.service.mapper;

import by.clevertec.persistence.entity.Product;
import by.clevertec.service.dto.ProductDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Class "ProductMapper" presents mapper class for the "Product" entity
 */
@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    Product mapToProduct(ProductDto productDto);
    ProductDto mapToProductDto(Product product);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateProductFromProductDto(ProductDto productDto,
                                               @MappingTarget Product product);
}
