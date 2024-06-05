package implementation.utils;

import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

  public static void saveToFile(String path, String content) {
    try (FileWriter writer = new FileWriter(path)) {
      writer.write(content);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
