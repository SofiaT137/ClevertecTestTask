package by.clevertec.service.exception;

public class CannotFindEntity extends RuntimeException{
    public CannotFindEntity(String message) {
        super(message);
    }
    public CannotFindEntity(String message, Long id) {
        super(message+id);
    }
}
