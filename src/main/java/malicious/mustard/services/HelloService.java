package malicious.mustard.services;

import javax.inject.Singleton;

@Singleton
public class HelloService {

    private static final String DEFAULT_NAME = "John Doe";
    
    public String sayHello(String name) {
        return "Hello " + (name != null ? name : DEFAULT_NAME);
    }
}
