package by.clevertec;

import by.clevertec.config.RestResponsePage;
import by.clevertec.persistence.entity.Product;
import by.clevertec.persistence.repository.ProductRepository;
import by.clevertec.service.dto.ProductDto;
import by.clevertec.service.exception.CannotFindEntityException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static by.clevertec.web.exception.ExceptionCodes.CANNOT_FIND_ENTITY;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class ProductTest {
    @LocalServerPort
    private int port;
    @Autowired
    private RestTemplate restTemplate;
    private String baseURL = "http://localhost";
    private static final String ID = "/{id}";
    @Autowired
    private ProductRepository productRepository;
    private static final HttpHeaders headers = new HttpHeaders();

    private final ProductDto productDto = new ProductDto()
            .setProductName("Lollipop")
            .setPrice(1.14)
            .setProductDiscountPercent(10);

    @BeforeEach
    public void setUp() {
        baseURL = baseURL
                .concat(":")
                .concat(port + "/")
                .concat("clevertecTestTask/v1/product");
    }

    @Test
    void testGetProductById() {
        ResponseEntity<ProductDto> productDtoResponseEntity =
                restTemplate.exchange(baseURL.concat(ID),
                        HttpMethod.GET, new HttpEntity<>(headers), ProductDto.class, 2);
        assertNotNull(productDtoResponseEntity);
    }

    @Test
    void testGetProductByIncorrectId() {
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> restTemplate.exchange(baseURL.concat(ID),
                        HttpMethod.GET, new HttpEntity<>(headers), ProductDto.class, 8));
        assertAll(
                () -> assertTrue(exception.getResponseBodyAsString()
                        .contains("Cannot find the product with id = 8")),
                () -> assertTrue(exception.getResponseBodyAsString().contains(CANNOT_FIND_ENTITY.getCodeNumber()))
        );
    }

    @Test
    @Order(1)
    void testInsertProduct() {
        restTemplate.postForEntity(baseURL, productDto,
                ProductDto.class);
        assertEquals(4, productRepository.findAll().size());
    }

    @Test
    @Order(2)
    void testDeleteProduct() {
        restTemplate.delete(baseURL + ID, 4);
        assertEquals(Optional.empty(), productRepository.findById(4L));
    }

    @Test
    void testUpdateProduct() {
        restTemplate.put(baseURL + ID, productDto, 1);
        Optional<Product> optionalProduct = productRepository.findById(1L);
        Product product = optionalProduct
                .orElseThrow(() -> new CannotFindEntityException("No entity!"));
        assertAll(
                () -> assertEquals(productDto.getProductName(), product.getProductName()),
                () -> assertEquals(productDto.getPrice(), product.getPrice()),
                () -> assertEquals(productDto.getProductDiscountPercent(), product.getProductDiscountPercent())
        );
    }

    @Test
    void testGetAllProducts() {
        ParameterizedTypeReference<RestResponsePage<ProductDto>> responseType
                = new ParameterizedTypeReference<>() {
        };
        ResponseEntity<RestResponsePage<ProductDto>> result =
                restTemplate.exchange(baseURL, HttpMethod.GET, null, responseType);
        List<ProductDto> searchResult = Objects.requireNonNull(result.getBody()).getContent();
        assertEquals(3, searchResult.size());
    }
}
