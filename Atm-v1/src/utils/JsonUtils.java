package utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class JsonUtils {

    public static String readFile(String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeFile(String path, String content) {
        try {
            Files.write(Paths.get(path), content.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
