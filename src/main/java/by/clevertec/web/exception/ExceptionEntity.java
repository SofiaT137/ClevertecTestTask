package by.clevertec.web.exception;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Class "ExceptionEntity" helps to create the "Exception" entity
 */
@Data
@Accessors(chain = true)
public class ExceptionEntity {
    private String exceptionMessage;
    private String exceptionCode;
}
