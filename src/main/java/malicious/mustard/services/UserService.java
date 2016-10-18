package malicious.mustard.services;

import malicious.mustard.repository.UsersRepository;
import malicious.mustard.transport.User;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class UserService {

    private final UsersRepository usersRepository;
    private List<String> userEmails;

    @Inject
    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public void createUser(User newUser) {
        assertUserEmail(newUser.getEmail());
        usersRepository.saveUser(newUser);
    }

    public List<String> getUserEmails() {
        return usersRepository.getAllEmails();
    }

    public User getUser(String email) {
        assertUserEmail(email);
        return usersRepository.findUser(email);
    }

    private void assertUserEmail(String email) {
        if (email == null || "".equals(email.trim())) {
            throw new IllegalArgumentException("Empty email is not allowed");
        }
    }
}
