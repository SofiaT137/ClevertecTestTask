package by.clevertec.service.fileCreator;

import java.io.IOException;

public interface FileCreator<T> {
    public void createFile(T t) throws IOException;
}
