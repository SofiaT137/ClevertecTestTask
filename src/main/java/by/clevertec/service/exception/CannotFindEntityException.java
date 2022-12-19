package by.clevertec.service.exception;

public class CannotFindEntityException extends RuntimeException{
    public CannotFindEntityException(String message) {
        super(message);
    }
    public CannotFindEntityException(String message, Long id) {
        super(message+id);
    }
}
