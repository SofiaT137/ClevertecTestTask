package by.clevertec.web.exception;

import by.clevertec.service.exception.CannotFindEntityException;
import by.clevertec.service.exception.EmptyItemListException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import static by.clevertec.web.exception.ExceptionCodes.CANNOT_FIND_ENTITY;
import static by.clevertec.web.exception.ExceptionCodes.EMPTY_ITEM_LIST;

@RestControllerAdvice
public class ExceptionHandler {
    private static final String STRING_MESSAGE = "Message: ";

    @org.springframework.web.bind.annotation.ExceptionHandler(CannotFindEntityException.class)
    public ResponseEntity<Object> cannotFindEntityExceptionHandling(CannotFindEntityException exception) {
        String exceptionMessage = exception.getMessage();
        ExceptionEntity exceptionEntity = new ExceptionEntity()
                .setExceptionMessage(exceptionMessage)
                .setExceptionCode(CANNOT_FIND_ENTITY.getCodeNumber());
        return new ResponseEntity<>(exceptionEntity, HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(EmptyItemListException.class)
    public ResponseEntity<Object> emptyItemListExceptionHandling(EmptyItemListException exception) {
        String exceptionMessage = exception.getMessage();
        ExceptionEntity exceptionEntity = new ExceptionEntity()
                .setExceptionMessage(exceptionMessage)
                .setExceptionCode(EMPTY_ITEM_LIST.getCodeNumber());
        return new ResponseEntity<>(exceptionEntity, HttpStatus.NOT_ACCEPTABLE);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> requestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        String exceptionMessage = exception.getLocalizedMessage();
        return new ResponseEntity<>(STRING_MESSAGE + exceptionMessage, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> noHandlerFoundException(NoHandlerFoundException exception) {
        String exceptionMessage = exception.getLocalizedMessage();
        return new ResponseEntity<>(STRING_MESSAGE + exceptionMessage, HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<Object> internalServerErrorException(Exception exception) {
        String exceptionMessage = exception.getMessage();
        return new ResponseEntity<>(STRING_MESSAGE + exceptionMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
