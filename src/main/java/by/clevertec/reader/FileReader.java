package by.clevertec.reader;

import java.util.List;

public interface FileReader {
    List<String> readFile(String path, String delimiter);
}
