package by.clevertec.util.fileCreator;

import java.io.IOException;

/**
 * "FileCreator" interface features "Creation the file" functionality
 * @param <T> The entity object
 */
public interface FileCreator<T> {
    /**
     * Method helps to create the File
     * @param t The entity object
     * @throws IOException exception
     */
    public void createFile(T t) throws IOException;
}
