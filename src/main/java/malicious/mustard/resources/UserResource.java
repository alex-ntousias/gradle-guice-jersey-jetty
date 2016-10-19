package malicious.mustard.resources;

import malicious.mustard.exceptions.BadRequestException;
import malicious.mustard.exceptions.NotFoundException;
import malicious.mustard.services.UserService;
import malicious.mustard.transport.User;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/users")
public class UserResource {

    private final UserService userService;

    @Inject
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void createUser(User newUser) {
        try {
            userService.createUser(newUser);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAllUsersWithoutTheirPasswords() {
        return userService.getUsersWithoutPasswords();
    }

    @GET
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("email") String email) {
        try {
            User user = userService.getUser(email);
            if (user == null) {
                throw new NotFoundException("Couldn't find user with email " + email);
            }

            return user;
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

}
