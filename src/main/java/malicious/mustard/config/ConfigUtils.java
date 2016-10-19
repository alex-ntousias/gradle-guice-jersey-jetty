package malicious.mustard.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtils {

    public static Properties loadFromFile(String propertiesFile) throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = DbConfig.class.getClassLoader().getResourceAsStream(propertiesFile);
        if (inputStream == null) {
            throw new FileNotFoundException(propertiesFile + " not found on the classpath");
        }
        properties.load(inputStream);

        return properties;
    }
}
