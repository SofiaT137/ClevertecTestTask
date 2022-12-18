package by.clevertec.service.impl;

import by.clevertec.persistence.entity.DiscountCard;
import by.clevertec.persistence.entity.Product;
import by.clevertec.persistence.repository.DiscountCardRepository;
import by.clevertec.persistence.repository.ProductRepository;
import by.clevertec.reader.impl.TxtFileReaderImpl;
import by.clevertec.service.CashReceiptService;
import by.clevertec.service.dto.CashReceiptDto;
import by.clevertec.service.dto.CashReceiptItemDto;
import by.clevertec.service.dto.CashierDto;
import by.clevertec.service.dto.DiscountCardDto;
import by.clevertec.service.dto.ShopDto;
import by.clevertec.service.exception.CannotFindDiscountCardException;
import by.clevertec.service.exception.CannotFindProductException;
import by.clevertec.service.exception.EmptyItemListException;
import by.clevertec.service.mapper.DiscountCardMapper;
import by.clevertec.service.fileCreator.impl.PdfCreatorImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CashReceiptServiceImpl implements CashReceiptService<CashReceiptDto> {
    private final DiscountCardRepository cardRepository;
    private final ProductRepository productRepository;
    private final DiscountCardMapper discountCardMapper;
    private final TxtFileReaderImpl fileReader;
    private final PdfCreatorImpl reader;
    private static final String CARD = "card";
    private static final String PRODUCT_ID = "productId";
    private static final Long CASHIER_ID = 1L;
    private static final int NUMBER_100 = 100;
    private static final int NUMBER_0 = 0;
    private static final int NUMBER_1 = 1;
    private static final int QTY_DISCOUNT_NUMBER = 5;
    private static final double QTY_DISCOUNT_VALUE = 0.9;
    private static final String SHOP_NAME = "SUPERMARKET 123";
    private static final String SHOP_ADDRESS = "12, MILKY WAY Galaxy/ Earth";
    private static final String SHOP_PHONE = "+375(17) 362-17-60";
    private static final String CANNOT_WRITE_IN_PDF_ERROR = "Cannot write in pdf!";
    private static final String EMPTY_LIST_ERROR = "You transferred an empty items list! Cash Receipt cannot be empty!";
    private static final String CANNOT_FIND_CARD_EXCEPTION = "Cannot find the card with transferred card id";
    private static final String CANNOT_FIND_PRODUCT_EXCEPTION = "Cannot find the product with transferred card id";
    private static final double SCALE = Math.pow(10, 2);
    private static final String POSITIVE_INTEGER_NUMBER_REGEX = "^[1-9]\\d*$";
    private static final String SPECIAL_SYMBOLS_REGEXP = "[\r\n]";
    private static final String EMPTY_STRING = "";
    private static final String SPACE_DELIMITER = " ";
    private static final String EQUALS_DELIMITER = "=";


    @Override
    public CashReceiptDto getCashReceiptByTransferredParameters(MultiValueMap<String, String> mapWithParameters){

        List<String> transferredProductIdList = mapWithParameters.get(PRODUCT_ID);
        List<String> transferredCardList = mapWithParameters.get(CARD);
        return getCashReceipt(transferredProductIdList,transferredCardList);
    }

  @Override
    public CashReceiptDto getCashReceiptByFileData(String path) throws FileNotFoundException {
      Pattern pattern = Pattern.compile(POSITIVE_INTEGER_NUMBER_REGEX);
      List<String> fileStrings = fileReader.readFile(path, SPACE_DELIMITER);
      List<String> cardStringId= new ArrayList<>();
      List<String> productStringId= new ArrayList<>();

      for (String str:
              fileStrings) {
          str = str.replaceAll(SPECIAL_SYMBOLS_REGEXP, EMPTY_STRING);
          String[] substring = str.split(EQUALS_DELIMITER);
          if (substring[NUMBER_0].equals(CARD)){
              cardStringId.add(substring[NUMBER_1]);
          } else if(pattern.matcher(substring[NUMBER_0]).matches()) {
              for (int i = NUMBER_0; i < Integer.parseInt(substring[NUMBER_1]); i++) {
                  productStringId.add(substring[NUMBER_0]);
              }
          }
      }
      return getCashReceipt(productStringId,cardStringId);
    }


    private CashReceiptDto getCashReceipt(List<String> transferredProductIdList,List<String> transferredCardList){

        if (transferredProductIdList == null){
            throw new EmptyItemListException(EMPTY_LIST_ERROR);
        }

        Map<Product, Long> productQtyMap = findAllItemsWithTheirQty(transferredProductIdList);

        if (transferredCardList == null) {
            return createCashReceipt(productQtyMap, null);
        }else{
            DiscountCardDto discountCardDto = discountCardMapper
                    .mapToDiscountCardDto(findDiscountCard(transferredCardList));
            return createCashReceipt(productQtyMap, discountCardDto);
        }
    }


    private CashReceiptDto createCashReceipt(Map<Product,Long> productQtyMap, DiscountCardDto discountCardDto) {

        CashReceiptDto cashReceiptDto = new CashReceiptDto()
                .setCashierDto(new CashierDto(CASHIER_ID))
                .setCashReceiptDate(LocalDate.now())
                .setCashReceiptTime(LocalTime.now())
                .setShopDto(new ShopDto(SHOP_NAME, SHOP_ADDRESS, SHOP_PHONE));

        if (discountCardDto != null) {
            cashReceiptDto
                    .setDiscountCardDto(discountCardDto);
            cashReceiptDto.setCashReceiptItemList(getAllCashReceiptItemList(productQtyMap, discountCardDto));
        } else {
            cashReceiptDto.setCashReceiptItemList(getAllCashReceiptItemList(productQtyMap, null));
        }
        cashReceiptDto.setTotalDiscount(getRoundDoubleValue(getTotalDiscount(cashReceiptDto
                .getCashReceiptItemList())));
        cashReceiptDto.setTotalPrice(getRoundDoubleValue(getTotalPrice(cashReceiptDto
                .getCashReceiptItemList())));

        createCashReceiptFile(cashReceiptDto);

        return cashReceiptDto;
    }


    private void createCashReceiptFile(CashReceiptDto cashReceiptDto) {
        try {
            reader.createFile(cashReceiptDto);
        } catch (IOException exception) {
            System.out.println(CANNOT_WRITE_IN_PDF_ERROR);
        }
    }

    private DiscountCard findDiscountCard(List<String> listWithCardId) {
        return listWithCardId
                .stream()
                .map(cardId -> cardRepository
                        .findById(Long.valueOf(cardId))
                        .orElseThrow(() ->new CannotFindDiscountCardException(CANNOT_FIND_CARD_EXCEPTION)))
                .max(Comparator.comparing(DiscountCard::getCardDiscountPercent))
                .orElseThrow(() ->new CannotFindDiscountCardException(CANNOT_FIND_CARD_EXCEPTION));
    }

    private Map<Product, Long> findAllItemsWithTheirQty(List<String> listWithProductsId) {
        return listWithProductsId
                .stream()
                .map(productId -> productRepository
                        .findById(Long.valueOf(productId))
                        .orElseThrow(()->new CannotFindProductException(CANNOT_FIND_PRODUCT_EXCEPTION)))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    private List<CashReceiptItemDto> getAllCashReceiptItemList(Map<Product, Long> mapWithItemsAndItemQty,
                                                               DiscountCardDto card) {
        return mapWithItemsAndItemQty
                .entrySet()
                .stream()
                .map(productQtyEntry -> getCashReceiptItem(productQtyEntry.getKey(), productQtyEntry.getValue(),
                        card))
                .collect(Collectors.toList());
    }

    private CashReceiptItemDto getCashReceiptItem(Product product, Long productQuantity, DiscountCardDto card) {
        return new CashReceiptItemDto(product.getProductName(), productQuantity,
                product.getPrice(), getTotalItemPrice(product, productQuantity, card));
    }

    private double getTotalItemPrice(Product product, Long productQuantity, DiscountCardDto discountCard) {
        double price = product.getPrice() * productQuantity;
        if (product.getProductDiscountPercent() != NUMBER_0) {
            price *= ((double) (NUMBER_100  - product.getProductDiscountPercent()) /NUMBER_100);
        } else if (productQuantity > QTY_DISCOUNT_NUMBER) {
            price *= QTY_DISCOUNT_VALUE;
        } else if (discountCard != null) {
            price *= ((double) (NUMBER_100 - discountCard.getCardDiscountPercent()) /NUMBER_100);
        }
        return getRoundDoubleValue(price);
    }

    private Double getTotalPrice(List<CashReceiptItemDto> cashReceiptItemList) {
        return cashReceiptItemList
                .stream()
                .mapToDouble(CashReceiptItemDto::getProductTotalPrice)
                .sum();
    }

    private Double getTotalDiscount(List<CashReceiptItemDto> cashReceiptItemList) {
        return cashReceiptItemList
                .stream()
                .mapToDouble(item -> item.getProductPrice() * item.getProductQuantity() -
                        item.getProductTotalPrice())
                .sum();
    }

    private Double getRoundDoubleValue(Double value) {
        return Math.ceil(value * SCALE) / SCALE;
    }
}






