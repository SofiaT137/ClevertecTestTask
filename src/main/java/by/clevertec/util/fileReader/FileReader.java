package by.clevertec.util.fileReader;

import java.util.List;

/**
 * "FileReader" interface features "Read the file" functionality
 */
public interface FileReader {
    /**
     * Method helps to read the File
     * @param path String path
     * @param delimiter String delimiter
     * @return List<String> file strings
     */
    List<String> readFile(String path, String delimiter);
}
