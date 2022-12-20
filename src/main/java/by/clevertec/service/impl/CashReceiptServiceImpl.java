package by.clevertec.service.impl;

import by.clevertec.service.DiscountCardService;
import by.clevertec.service.ProductService;
import by.clevertec.service.exception.CannotWriteInPdfException;
import by.clevertec.util.fileReader.impl.TxtFileReaderImpl;
import by.clevertec.service.CashReceiptService;
import by.clevertec.service.dto.CashReceiptDto;
import by.clevertec.service.dto.CashReceiptItemDto;
import by.clevertec.service.dto.CashierDto;
import by.clevertec.service.dto.DiscountCardDto;
import by.clevertec.service.dto.ProductDto;
import by.clevertec.service.dto.ShopDto;
import by.clevertec.service.exception.CannotFindEntityException;
import by.clevertec.service.exception.EmptyItemListException;
import by.clevertec.util.fileCreator.impl.PdfCreatorImpl;
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

    private final DiscountCardService<DiscountCardDto> discountCardService;
    private final ProductService<ProductDto> productService;
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
    private static final String CANNOT_WRITE_IN_PDF_ERROR = "Cannot write in pdf! Reason: ";
    private static final String EMPTY_LIST_ERROR = "You transferred an empty items list! Cash Receipt cannot be empty!";
    private static final String CANNOT_FIND_CARD_EXCEPTION = "Cannot find the discount card with max value!";
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
          } else if(pattern
                  .matcher(substring[NUMBER_0])
                  .matches()) {
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

        Map<ProductDto, Long> productQtyMap = findAllItemsWithTheirQty(transferredProductIdList);

        if (transferredCardList == null) {
            return createCashReceipt(productQtyMap, null);
        }else{
            DiscountCardDto discountCardDto = findDiscountCard(transferredCardList);
            return createCashReceipt(productQtyMap, discountCardDto);
        }
    }


    private CashReceiptDto createCashReceipt(Map<ProductDto,Long> productQtyMap, DiscountCardDto discountCardDto) {

        CashReceiptDto cashReceiptDto = new CashReceiptDto()
                .setCashierDto(new CashierDto().setCashierId(CASHIER_ID))
                .setCashReceiptDate(LocalDate.now())
                .setCashReceiptTime(LocalTime.now())
                .setShopDto(new ShopDto()
                        .setShopName(SHOP_NAME)
                        .setShopAddress(SHOP_ADDRESS)
                        .setShopPhone(SHOP_PHONE));

        if (discountCardDto != null) {
            cashReceiptDto.setDiscountCardDto(discountCardDto);
            cashReceiptDto.setCashReceiptItemList(getAllCashReceiptItemList(productQtyMap, discountCardDto));
        } else {
            cashReceiptDto.setCashReceiptItemList(getAllCashReceiptItemList(productQtyMap, null));
        }
        cashReceiptDto
                .setTotalDiscount(getRoundDoubleValue(getTotalDiscount(cashReceiptDto
                .getCashReceiptItemList())));
        cashReceiptDto
                .setTotalPrice(getRoundDoubleValue(getTotalPrice(cashReceiptDto
                .getCashReceiptItemList())));

        createCashReceiptFile(cashReceiptDto);

        return cashReceiptDto;
    }


    private void createCashReceiptFile(CashReceiptDto cashReceiptDto) {
        try {
            reader.createFile(cashReceiptDto);
        } catch (IOException exception) {
            throw new CannotWriteInPdfException(CANNOT_WRITE_IN_PDF_ERROR + exception.getMessage());
        }
    }

    private DiscountCardDto findDiscountCard(List<String> listWithCardId) {
        return listWithCardId
                .stream()
                .map(cardId -> discountCardService
                        .getById(Long.valueOf(cardId)))
                        .max(Comparator.comparing(DiscountCardDto::getCardDiscountPercent))
                .orElseThrow(() ->new CannotFindEntityException(CANNOT_FIND_CARD_EXCEPTION));
    }

    private Map<ProductDto, Long> findAllItemsWithTheirQty(List<String> listWithProductsId) {
        return listWithProductsId
                .stream()
                .map(productId -> productService
                        .getById(Long.valueOf(productId)))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    private List<CashReceiptItemDto> getAllCashReceiptItemList(Map<ProductDto, Long> mapWithItemsAndItemQty,
                                                               DiscountCardDto card) {
        return mapWithItemsAndItemQty
                .entrySet()
                .stream()
                .map(productQtyEntry -> getCashReceiptItem(productQtyEntry.getKey(), productQtyEntry.getValue(),
                        card))
                .collect(Collectors.toList());
    }

    private CashReceiptItemDto getCashReceiptItem(ProductDto product, Long productQuantity, DiscountCardDto card) {
        return new CashReceiptItemDto()
                .setProductName(product.getProductName())
                .setProductQuantity(productQuantity)
                .setProductPrice(product.getPrice())
                .setProductTotalPrice(getTotalItemPrice(product,productQuantity,card));
    }

    private double getTotalItemPrice(ProductDto product, Long productQuantity, DiscountCardDto discountCard) {
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






