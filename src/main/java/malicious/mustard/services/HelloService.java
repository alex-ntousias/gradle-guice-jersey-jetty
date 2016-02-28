package malicious.mustard.services;

import javax.inject.Singleton;

import malicious.mustard.transport.Greeting;

@Singleton
public class HelloService {

    private static final String DEFAULT_NAME = "John Doe";
    
    public Greeting sayHello(String name) {
        return new Greeting(name != null ? name : DEFAULT_NAME);
    }
}
