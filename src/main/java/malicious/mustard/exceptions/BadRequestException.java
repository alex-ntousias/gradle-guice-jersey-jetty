package malicious.mustard.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@SuppressWarnings("serial")
public class BadRequestException extends WebApplicationException {

    public BadRequestException(String message) {
        super(Response.status(Response.Status.BAD_REQUEST).entity(message).type(MediaType.TEXT_PLAIN).build());
    }

}
