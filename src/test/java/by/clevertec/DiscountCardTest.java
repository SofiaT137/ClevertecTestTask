package by.clevertec;

import by.clevertec.config.RestResponsePage;
import by.clevertec.persistence.entity.DiscountCard;
import by.clevertec.persistence.repository.DiscountCardRepository;
import by.clevertec.service.dto.DiscountCardDto;
import by.clevertec.service.exception.CannotFindEntityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class DiscountCardTest {
    @LocalServerPort
    private int port;
    @Autowired
    private RestTemplate restTemplate;
    private String baseURL = "http://localhost";
    private static final String ID = "/{id}";

    @Autowired
    private DiscountCardRepository discountCardRepository;
    private static final HttpHeaders headers = new HttpHeaders();

    private final DiscountCardDto discountCardDtoTest = new DiscountCardDto()
            .setCardDiscountPercent(5);

    @BeforeEach
    public void setUp() {
        baseURL = baseURL
                .concat(":")
                .concat(port + "/")
                .concat("clevertecTestTask/v1/discountCard");
    }

    @Test
    void testGetDiscountCardById() {
        ResponseEntity<DiscountCardDto> discountCardDtoResponseEntity =
                restTemplate.exchange(baseURL.concat(ID),
                        HttpMethod.GET, new HttpEntity<>(headers), DiscountCardDto.class, 2);
        assertNotNull(discountCardDtoResponseEntity);
    }

    @Test
    void testGetDiscountCardByIncorrectId() {
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> restTemplate.exchange(baseURL.concat(ID),
                        HttpMethod.GET, new HttpEntity<>(headers), DiscountCardDto.class, 8));
        assertAll(
                () -> assertTrue(exception.getResponseBodyAsString()
                        .contains("Cannot find the discount card with id = 8")),
                () -> assertTrue(exception.getResponseBodyAsString().contains(CANNOT_FIND_ENTITY.getCodeNumber()))
        );
    }

    @Test
    @Order(1)
    void testInsertDiscountCard() {
        restTemplate.postForEntity(baseURL, discountCardDtoTest,
                DiscountCardDto.class);
        assertEquals(4, discountCardRepository.findAll().size());
    }

    @Test
    @Order(2)
    void testDeleteDiscountCard() {
        restTemplate.delete(baseURL + ID, 4);
        assertEquals(Optional.empty(), discountCardRepository.findById(4L));
    }

    @Test
    void testUpdateDiscountCard() {
        restTemplate.put(baseURL + ID, discountCardDtoTest, 1);
        Optional<DiscountCard> discountCard = discountCardRepository.findById(1L);
        Integer discountCardPercent = discountCard
                .orElseThrow(() -> new CannotFindEntityException("No such entity!"))
                .getCardDiscountPercent();
        assertEquals(discountCardDtoTest.getCardDiscountPercent(), discountCardPercent);
    }

    @Test
    void testGetAllDiscountCards() {
        ParameterizedTypeReference<RestResponsePage<DiscountCardDto>> responseType
                = new ParameterizedTypeReference<>() {
        };
        ResponseEntity<RestResponsePage<DiscountCardDto>> result =
                restTemplate.exchange(baseURL, HttpMethod.GET, null, responseType);
        List<DiscountCardDto> searchResult = Objects.requireNonNull(result.getBody()).getContent();
        assertEquals(3, searchResult.size());
    }
}
