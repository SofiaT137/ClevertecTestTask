package by.clevertec.service.exception;

/**
 * Class "CannotWriteInPdfException" extends "RuntimeException" and presents creating
 * of the "CannotWriteInPdfException" exception
 */
public class CannotWriteInPdfException extends RuntimeException  {
    public CannotWriteInPdfException(String message) {
        super(message);
    }
}
