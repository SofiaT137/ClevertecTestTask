package by.clevertec.web.exception;

import by.clevertec.service.exception.CannotFindEntityException;
import by.clevertec.service.exception.CannotWriteInPdfException;
import by.clevertec.service.exception.EmptyItemListException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import static by.clevertec.web.exception.ExceptionCodes.CANNOT_FIND_ENTITY;
import static by.clevertec.web.exception.ExceptionCodes.CANNOT_WRITE_IN_PDF;
import static by.clevertec.web.exception.ExceptionCodes.EMPTY_ITEM_LIST;


/**
 * Class "ExceptionsHandler" allows interception and handling of exceptions across the project
 */
@RestControllerAdvice
public class ExceptionsHandler {
    private static final String STRING_MESSAGE = "Message: ";

    /**
     * Method "cannotFindEntityExceptionHandling" handles the "CannotFindEntityException" exception
     * @param exception "CannotFindEntityException" exception
     * @return Response entity with "ExceptionEntity" entity and HttpStatus "NOT_FOUND"
     */
    @ExceptionHandler(CannotFindEntityException.class)
    public ResponseEntity<Object> cannotFindEntityExceptionHandling(CannotFindEntityException exception) {
        String exceptionMessage = exception.getMessage();
        ExceptionEntity exceptionEntity = new ExceptionEntity()
                .setExceptionMessage(exceptionMessage)
                .setExceptionCode(CANNOT_FIND_ENTITY.getCodeNumber());
        return new ResponseEntity<>(exceptionEntity, HttpStatus.NOT_FOUND);
    }

    /**
     * Method "emptyItemListExceptionHandling" handles the "EmptyItemListException" exception
     * @param exception "EmptyItemListException" exception
     * @return Response entity with "ExceptionEntity" entity and HttpStatus "NOT_ACCEPTABLE"
     */
    @ExceptionHandler(EmptyItemListException.class)
    public ResponseEntity<Object> emptyItemListExceptionHandling(EmptyItemListException exception) {
        String exceptionMessage = exception.getMessage();
        ExceptionEntity exceptionEntity = new ExceptionEntity()
                .setExceptionMessage(exceptionMessage)
                .setExceptionCode(EMPTY_ITEM_LIST.getCodeNumber());
        return new ResponseEntity<>(exceptionEntity, HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * Method "CannotWriteInPdfException" handles the "CannotWriteInPdfException" exception
     * @param exception "CannotWriteInPdfException" exception
     * @return Response entity with "ExceptionEntity" entity and HttpStatus "BAD_REQUEST"
     */
    @ExceptionHandler(CannotWriteInPdfException.class)
    public ResponseEntity<Object> CannotWriteInPdfExceptionHandling(CannotWriteInPdfException exception) {
        String exceptionMessage = exception.getMessage();
        ExceptionEntity exceptionEntity = new ExceptionEntity()
                .setExceptionMessage(exceptionMessage)
                .setExceptionCode(CANNOT_WRITE_IN_PDF.getCodeNumber());
        return new ResponseEntity<>(exceptionEntity, HttpStatus.BAD_REQUEST);
    }

    /**
     * Method "requestMethodNotSupportedException" handles the "HttpRequestMethodNotSupportedException" exception
     * @param exception "HttpRequestMethodNotSupportedException" exception
     * @return Response entity with exception message and HttpStatus "METHOD_NOT_ALLOWED"
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> requestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        String exceptionMessage = exception.getLocalizedMessage();
        return new ResponseEntity<>(STRING_MESSAGE + exceptionMessage, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * Method "noHandlerFoundException" handles the "NoHandlerFoundException" exception
     * @param exception "NoHandlerFoundException" exception
     * @return Response entity with exception message and HttpStatus "NOT_FOUND"
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> noHandlerFoundException(NoHandlerFoundException exception) {
        String exceptionMessage = exception.getLocalizedMessage();
        return new ResponseEntity<>(STRING_MESSAGE + exceptionMessage, HttpStatus.NOT_FOUND);
    }

    /**
     * Method "internalServerErrorException" handles all the exceptions with status code "5XX"
     * @param exception "Exception" exception
     * @return Response entity with exception message and HttpStatus "INTERNAL_SERVER_ERROR"
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> internalServerErrorException(Exception exception) {
        String exceptionMessage = exception.getMessage();
        return new ResponseEntity<>(STRING_MESSAGE + exceptionMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
