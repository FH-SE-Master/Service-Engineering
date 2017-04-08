package sve2.fhbay.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;

public class LoggingUtil {
  public static void initJdkLogging(String loggingPropertiesFile) {
    InputStream is = null;
    try {
      is = LoggingUtil.class.getClassLoader().getResourceAsStream(loggingPropertiesFile);
      if (is != null)
        LogManager.getLogManager().readConfiguration(is);
      else
        System.err.printf("File \"%s\" not found.%n", loggingPropertiesFile);
    }
    catch (IOException e) {
      System.err.println(e);
    }
    finally {
      if (is != null)
        try { is.close(); } catch(IOException e) {}
    }
  }
}
