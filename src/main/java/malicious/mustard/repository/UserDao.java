package malicious.mustard.repository;

import malicious.mustard.repository.mappers.UserMapper;
import malicious.mustard.transport.User;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class UserDao {

    public static final String TABLE_NAME = "USERS";
    private static final String SELECT_ALL = "SELECT * FROM " + TABLE_NAME;

    private final DBI dbi;
    private final UserMapper mapper;

    @Inject
    public UserDao(DBI dbi, UserMapper mapper) {
        this.dbi = dbi;
        this.mapper = mapper;

        createTable();
    }

    private void createTable() {
        try (Handle h = dbi.open()) {
            h.execute("CREATE TABLE users (EMAIL VARCHAR(100) PRIMARY KEY, PASSWORD VARCHAR(100), DISPLAY_NAME VARCHAR(100))");
        }
    }

    public void saveUser(User user) {
        try (Handle h = dbi.open()) {
            if (findUser(user.getEmail()) != null) {
                throw new IllegalArgumentException("There is already a user with this email");
            }
            h.execute("INSERT INTO USERS (EMAIL, PASSWORD, DISPLAY_NAME) VALUES (?, ?, ?)",
                    user.getEmail(), user.getPassword(), user.getDisplayName());
        }
    }

    public User findUser(String email) {
        try (Handle h = dbi.open()) {
            User user = h.createQuery(SELECT_ALL + " where email = :email")
                    .bind("email", email)
                    .map(mapper)
                    .first();

            return user;
        }
    }

    public List<User> getAllUsers() {
        try (Handle h = dbi.open()) {
            List<User> users = h.createQuery(SELECT_ALL)
                    .map(mapper)
                    .list();
            return users;
        }
    }
}
