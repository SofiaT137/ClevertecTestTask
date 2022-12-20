package by.clevertec.service.impl;

import by.clevertec.persistence.entity.Product;
import by.clevertec.persistence.repository.ProductRepository;
import by.clevertec.service.ProductService;
import by.clevertec.service.dto.ProductDto;
import by.clevertec.service.exception.CannotFindEntityException;
import by.clevertec.service.mapper.ProductMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * Class "ProductServiceImpl" is an implementation of the ProductService interface
 * The class presents service logic layer for the "Product" entity
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService<ProductDto> {

    private static final String CANNOT_FIND_PRODUCT_EXCEPTION = "Cannot find the product with id = ";
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public void insert(ProductDto productDto) {
        productRepository.save(productMapper.mapToProduct(productDto));
    }
    @Override
    public ProductDto getById(Long id) {
        return productMapper.mapToProductDto(productRepository
                        .findById(id)
                        .orElseThrow(() -> new CannotFindEntityException(CANNOT_FIND_PRODUCT_EXCEPTION, id)));
    }

    @Override
    public Page<ProductDto> getAll(int pageNumber, int pageSize) {
        Page<Product> products = productRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return products.map(productMapper::mapToProductDto);
    }

    @Override
    @Transactional
    public void update(Long id, ProductDto productDto) {
        Optional<Product> product = productRepository.findById(id);
        Product product1 = product.get();
        productMapper.updateProductFromProductDto(productDto, product1);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
