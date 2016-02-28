package malicious.mustard;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {
    
    public void sayHello() {
        System.out.println("Hello world!");
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector();
        
        Main main = injector.getInstance(Main.class);
        main.sayHello();
    }
    
}
