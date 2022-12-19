package by.clevertec.util.fileReader.impl;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class TxtFileReaderImpl {

    public List<String> readFile(String path,String delimiter) throws FileNotFoundException {

        List<String> fileStringsAfterUsingDelimiter = new ArrayList<>();

        Scanner scanner = new Scanner(new File(path));

        scanner.useDelimiter(delimiter);

        while (scanner.hasNext()){
            fileStringsAfterUsingDelimiter.add(scanner.next());
        }
        scanner.close();

        return fileStringsAfterUsingDelimiter;
    }
}
