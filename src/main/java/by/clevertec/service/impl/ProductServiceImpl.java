package by.clevertec.service.impl;

import by.clevertec.persistence.entity.Product;
import by.clevertec.persistence.repository.ProductRepository;
import by.clevertec.service.ProductService;
import by.clevertec.service.dto.ProductDto;
import by.clevertec.service.exception.CannotFindEntity;
import by.clevertec.service.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService<ProductDto> {

    private static final String CANNOT_FIND_PRODUCT_EXCEPTION = "Cannot find the product with id = ";
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public void insert(ProductDto productDto) {
        productRepository
                .save(productMapper
                        .mapToProduct(productDto));
    }
    @Override
    public ProductDto getById(Long id) {
        return productMapper
                .mapToProductDto(productRepository
                        .findById(id)
                        .orElseThrow(() -> new CannotFindEntity(CANNOT_FIND_PRODUCT_EXCEPTION, id)));
    }

    @Override
    public Page<ProductDto> getAll(int pageNumber, int pageSize) {
        Page<Product> products = productRepository
                .findAll(PageRequest
                        .of(pageNumber, pageSize));
        return products
                .map(productMapper::mapToProductDto);
    }

    @Override
    public void update(Long id, ProductDto productDto) {
        Product product = productMapper
                .mapToProduct(getById(id));
        productMapper
                .updateProductFromProductDto(productDto, product);
    }

    @Override
    public void delete(Long id) {
        productRepository
                .deleteById(id);
    }
}
