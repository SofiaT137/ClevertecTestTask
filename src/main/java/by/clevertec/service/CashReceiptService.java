package by.clevertec.service;

public interface CashReceiptService<T> extends CRUDService<T> {
    void getCashReceipt(T dto);
}
