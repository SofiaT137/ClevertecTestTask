package by.clevertec;

import by.clevertec.service.dto.CashReceiptDto;
import by.clevertec.service.dto.CashierDto;
import by.clevertec.service.dto.ShopDto;
import by.clevertec.web.payload.request.FileInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static by.clevertec.web.exception.ExceptionCodes.EMPTY_ITEM_LIST;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CashReceiptTest {
    @LocalServerPort
    private int port;
    @Autowired
    private RestTemplate restTemplate;
    private String baseURL = "http://localhost";
    private static final HttpHeaders headers = new HttpHeaders();

    private static final ShopDto SHOP_DTO_TEST = new ShopDto()
            .setShopName("SUPERMARKET 123")
            .setShopAddress("12, MILKY WAY Galaxy/ Earth")
            .setShopPhone("+375(17) 362-17-60");
    private static final CashierDto CASHIER_DTO_TEST = new CashierDto().setCashierId(1L);
    private final FileInfo fileInfo = new FileInfo().setPath("D:/clevertec.txt");
//    private final String pathToTheDocument = ;

    @BeforeEach
    public void setUp() {
        baseURL = baseURL
                .concat(":")
                .concat(port + "/")
                .concat("clevertecTestTask/v1/cashReceipt");
    }

    @Test
    void getCashReceiptByTransferredParametersTest() {
        ResponseEntity<CashReceiptDto> cashReceiptDtoResponseEntity =
                restTemplate.exchange(baseURL.concat("?productId=1&productId=2&productId=1&card=1"),
                        HttpMethod.GET, new HttpEntity<>(headers), CashReceiptDto.class);
        assertAll(
                () -> assertTrue(cashReceiptDtoResponseEntity.getStatusCode().is2xxSuccessful()),
                () -> assertEquals(Objects.requireNonNull(cashReceiptDtoResponseEntity
                                .getBody())
                        .getShopDto(), SHOP_DTO_TEST),
                () -> assertEquals(Objects.requireNonNull(cashReceiptDtoResponseEntity
                                .getBody())
                        .getCashierDto(), CASHIER_DTO_TEST),
                () -> assertEquals(2, Objects.requireNonNull(cashReceiptDtoResponseEntity
                                .getBody())
                        .getCashReceiptItemList().size()),
                () -> assertEquals(1, Objects.requireNonNull(cashReceiptDtoResponseEntity
                                .getBody())
                        .getDiscountCardDto().getId()),
                () -> assertEquals(21.34, Objects.requireNonNull(cashReceiptDtoResponseEntity
                                .getBody())
                        .getTotalPrice()),
                () -> assertEquals(1.32, Objects.requireNonNull(cashReceiptDtoResponseEntity
                                .getBody())
                        .getTotalDiscount()));
    }

    @Test
    void getCashReceiptByFileDataTest() {
        ResponseEntity<CashReceiptDto> cashReceiptDtoResponseEntity = restTemplate.postForEntity(baseURL, fileInfo,
                CashReceiptDto.class);
        assertAll(
                () -> assertTrue(cashReceiptDtoResponseEntity.getStatusCode().is2xxSuccessful()),
                () -> assertEquals(Objects.requireNonNull(cashReceiptDtoResponseEntity
                                .getBody())
                        .getShopDto(), SHOP_DTO_TEST),
                () -> assertEquals(Objects.requireNonNull(cashReceiptDtoResponseEntity
                                .getBody())
                        .getCashierDto(), CASHIER_DTO_TEST),
                () -> assertEquals(3, Objects.requireNonNull(cashReceiptDtoResponseEntity
                                .getBody())
                        .getCashReceiptItemList().size()),
                () -> assertEquals(1, Objects.requireNonNull(cashReceiptDtoResponseEntity
                                .getBody())
                        .getDiscountCardDto().getId()),
                () -> assertEquals(63.84, Objects.requireNonNull(cashReceiptDtoResponseEntity
                                .getBody())
                        .getTotalPrice()),
                () -> assertEquals(5.32, Objects.requireNonNull(cashReceiptDtoResponseEntity
                                .getBody())
                        .getTotalDiscount()));;
    }
    @Test
    void checkIfTransferredEmptyListTest() {
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> restTemplate.exchange(baseURL,
                        HttpMethod.GET, new HttpEntity<>(headers), CashReceiptDto.class));
        assertAll(
                () -> assertTrue(exception.getResponseBodyAsString()
                        .contains("You transferred an empty items list! Cash Receipt cannot be empty!")),
                () -> assertTrue(exception.getResponseBodyAsString().contains(EMPTY_ITEM_LIST.getCodeNumber()))
        );
    }
}
