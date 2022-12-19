package by.clevertec.util.fileCreator;

import java.io.IOException;

public interface FileCreator<T> {
    public void createFile(T t) throws IOException;
}
