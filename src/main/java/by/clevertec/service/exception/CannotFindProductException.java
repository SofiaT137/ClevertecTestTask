package by.clevertec.service.exception;

public class CannotFindProductException extends RuntimeException{
    public CannotFindProductException(String message) {
        super(message);
    }
}
