package by.clevertec.service;

import org.springframework.util.MultiValueMap;

public interface CashReceiptService<T>{
    T getCashReceipt(MultiValueMap<String, String> mapWithParameters);
}
