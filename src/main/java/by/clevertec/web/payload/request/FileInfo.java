package by.clevertec.web.payload.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Class "FileInfo" helps to create the "FileInfo" entity
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class FileInfo {
    private String path;
}
