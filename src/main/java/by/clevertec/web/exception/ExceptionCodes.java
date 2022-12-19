package by.clevertec.web.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ExceptionCodes {

    CANNOT_FIND_ENTITY("404001"),
    EMPTY_ITEM_LIST("406001");

    @Getter
    private final String codeNumber;

}
