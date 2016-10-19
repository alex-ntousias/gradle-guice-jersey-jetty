package malicious.mustard.config;

import javax.inject.Singleton;
import java.io.IOException;
import java.util.Properties;

@Singleton
public class DbConfig {

    private static final String PROPERTIES_FILE = "db.properties";

    private final Properties properties;

    public DbConfig() throws IOException {
        this(ConfigUtils.loadFromFile(PROPERTIES_FILE));
    }

    public DbConfig(Properties properties) {
        this.properties = properties;
    }

    public String getDataSourceClass() {
        return properties.getProperty("db.dataSourceClass", "");
    }

    public String getUser() {
        return properties.getProperty("db.user", "user");
    }

    public String getPassword() {
        return properties.getProperty("db.password", "password");
    }
}
