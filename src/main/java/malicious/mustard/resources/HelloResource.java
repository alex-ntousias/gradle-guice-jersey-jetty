package malicious.mustard.resources;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import malicious.mustard.services.HelloService;

@Path("/hello")
public class HelloResource {

    private HelloService helloService;

    @Inject
    public HelloResource(HelloService helloService) {
        this.helloService = helloService;
    }
        
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello(@QueryParam("name") String name) {
        return helloService.sayHello(name);
    }
    
}
