package malicious.mustard.infrastructure.jersey;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import com.google.inject.Scopes;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class JerseyModule extends JerseyServletModule {

    @Override
    protected void configureServlets() {
        bind(GuiceContainer.class);
        bind(JacksonJsonProvider.class).in(Scopes.SINGLETON);
        
        PackagesResourceConfig resourceConfig = new PackagesResourceConfig("malicious.mustard.resources");
        for (Class<?> resource : resourceConfig.getClasses()) {
            bind(resource);
        }
        
        serve("/*").with(GuiceContainer.class);
    }
    
}
