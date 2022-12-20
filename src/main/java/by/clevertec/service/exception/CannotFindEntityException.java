package by.clevertec.service.exception;

/**
 * Class "CannotFindEntityException" extends "RuntimeException" and presents creating
 * of the "CannotFindEntityException" exception
 */
public class CannotFindEntityException extends RuntimeException{
    public CannotFindEntityException(String message) {
        super(message);
    }
    public CannotFindEntityException(String message, Long id) {
        super(message+id);
    }
}
