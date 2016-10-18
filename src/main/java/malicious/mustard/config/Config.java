package malicious.mustard.config;

import javax.inject.Singleton;

@Singleton
public class Config {

    public String getDataSourceClass() {
        return "jdbc:h2:mem:test2";
    }

    public String getUser() {
        return "username";
    }

    public String getPassword() {
        return "password";
    }
}
