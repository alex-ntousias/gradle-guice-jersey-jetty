package malicious.mustard.transport;

import org.codehaus.jackson.annotate.JsonProperty;

public class Greeting {

    private String name;

    public Greeting(String name) {
        this.name = name;
    }
    
    @JsonProperty
    public String getMessage() {
        return "Hello " + name + "!!!";
    }    
    
}
