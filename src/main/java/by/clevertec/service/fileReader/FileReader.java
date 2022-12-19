package by.clevertec.service.fileReader;

import java.util.List;

public interface FileReader {
    List<String> readFile(String path, String delimiter);
}
