package malicious.mustard.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class HelloResource {

    private static final String DEFAULT_NAME = "John Doe";
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello(@QueryParam("name") String nameParam) {
        String name = nameParam != null ? nameParam : DEFAULT_NAME;
        return "Hello " + name;
    }
    
}
