package malicious.mustard;

import malicious.mustard.infrastructure.db.DbModule;
import malicious.mustard.infrastructure.jersey.JerseyModule;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceFilter;

public class Main {
    
    private static final int DEFAULT_PORT = 8080;
    
    public void startServer(int serverPort) throws Exception {
        Server server = new Server(serverPort);
        ServletContextHandler servletContextHandler = new ServletContextHandler(server, "/");
        servletContextHandler.addFilter(GuiceFilter.class, "/*", null);
        servletContextHandler.addServlet(DefaultServlet.class, "/");
        
        server.start();
        server.join();
    }

    public static void main(String[] args) throws Exception {
        Injector injector = Guice.createInjector(
                new JerseyModule(),
                new DbModule()
        );
        
        Main main = injector.getInstance(Main.class);
        main.startServer(DEFAULT_PORT);
    }
    
}
