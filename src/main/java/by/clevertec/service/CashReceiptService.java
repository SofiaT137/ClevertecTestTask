package by.clevertec.service;

import org.springframework.util.MultiValueMap;

import java.io.FileNotFoundException;

/**
 * "CashReceiptService" interface features "CashReceipt service functionality
 * @param <T> The entity object
 */
public interface CashReceiptService<T>{

    /**
     * Method helps to create CashReceiptDto object and save it in .pdf file
     * @param mapWithParameters MultiValueMap map
     * @return T The entity object
     */
    T getCashReceiptByTransferredParameters(MultiValueMap<String, String> mapWithParameters);

    /**
     * Method reads file from path, takes values from .txt file,
     * helps to create CashReceiptDto object and save it in .pdf file
     * @param path String path
     * @return T The entity object
     * @throws FileNotFoundException exception
     */
    T getCashReceiptByFileData(String path) throws FileNotFoundException;
}
