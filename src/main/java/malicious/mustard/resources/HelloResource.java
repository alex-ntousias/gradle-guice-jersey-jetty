package malicious.mustard.resources;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import malicious.mustard.services.HelloService;
import malicious.mustard.transport.Greeting;

@Path("/hello")
public class HelloResource {

    private HelloService helloService;

    @Inject
    public HelloResource(HelloService helloService) {
        this.helloService = helloService;
    }
        
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Greeting sayHello(@QueryParam("name") String name) {
        return helloService.sayHello(name);
    }
    
}
