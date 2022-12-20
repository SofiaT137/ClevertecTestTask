package by.clevertec.service.exception;

/**
 * Class "EmptyItemListException" extends "RuntimeException" and presents creating
 * of the "EmptyItemListException" exception
 */
public class EmptyItemListException extends RuntimeException {
    public EmptyItemListException(String message) {
        super(message);
    }
}
