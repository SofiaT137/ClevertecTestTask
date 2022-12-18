package by.clevertec.service;

import org.springframework.util.MultiValueMap;

import java.io.FileNotFoundException;

public interface CashReceiptService<T>{
    T getCashReceiptByTransferredParameters(MultiValueMap<String, String> mapWithParameters);

    T getCashReceiptByFileData(String path) throws FileNotFoundException;
}
