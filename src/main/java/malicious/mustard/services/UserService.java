package malicious.mustard.services;

import malicious.mustard.repository.UserDao;
import malicious.mustard.transport.User;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Singleton
public class UserService {

    private final UserDao userDao;
    private List<String> userEmails;

    @Inject
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void createUser(User newUser) {
        assertUserEmail(newUser.getEmail());
        userDao.saveUser(newUser);
    }

    public List<User> getUsersWithoutPasswords() {
        return userDao.getAllUsers().stream()
                .map(u -> new User(u.getEmail(), null, u.getDisplayName()))
                .collect(toList());
    }

    public User getUser(String email) {
        assertUserEmail(email);
        return userDao.findUser(email);
    }

    private void assertUserEmail(String email) {
        if (email == null || "".equals(email.trim())) {
            throw new IllegalArgumentException("Empty email is not allowed");
        }
    }
}
