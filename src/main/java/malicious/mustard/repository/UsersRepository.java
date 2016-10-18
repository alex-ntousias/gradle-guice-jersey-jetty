package malicious.mustard.repository;

import malicious.mustard.repository.mappers.UserMapper;
import malicious.mustard.transport.User;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.util.StringColumnMapper;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class UsersRepository {

    private final Handle handle;
    private UserMapper mapper;

    @Inject
    public UsersRepository(DBI dbi, UserMapper mapper) {
        handle = dbi.open();
        this.mapper = mapper;

        createTable();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> handle.close()));
    }

    private void createTable() {
        handle.execute("create table users (email varchar(100) primary key, password varchar(100), display_name varchar(100))");
    }

    public void saveUser(User user) {
        handle.execute("insert into users (email, password, display_name) values (?, ?, ?)",
                user.getEmail(), user.getPassword(), user.getDisplayName());
    }

    public User findUser(String email) {
        User user = handle.createQuery("select email, password, display_name from users where email = :email")
                .bind("email", email)
                .map(mapper)
                .first();

        return user;
    }

    public List<String> getAllEmails() {
        List<String> emails = handle.createQuery("select email from users").map(StringColumnMapper.INSTANCE).list();
        System.out.println(emails);
        return emails;
    }
}
