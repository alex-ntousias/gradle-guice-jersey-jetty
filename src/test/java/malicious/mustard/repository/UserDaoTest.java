package malicious.mustard.repository;

import malicious.mustard.IntegrationRunner;
import malicious.mustard.infrastructure.db.DbModule;
import malicious.mustard.repository.mappers.UserMapper;
import malicious.mustard.transport.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import javax.inject.Inject;

import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

@RunWith(IntegrationRunner.class)
@IntegrationRunner.GuiceModules({DbModule.class})
public class UserDaoTest {

    @Inject
    private UserDao userDao;

    @Inject
    private DBI dbi;

    @Inject
    private UserMapper userMapper;

    @Before
    public void setup() {
        try (Handle h = dbi.open()) {
            h.execute("DELETE FROM " + userDao.TABLE_NAME);
        }
    }

    @Test
    public void saveUser_shouldSaveUserInDb() {
        User user = new User("a@a.com", "passwd", "a");
        userDao.saveUser(user);

        assertThat(retrieveUser("a@a.com"), is(user));
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveUser_shouldThrowExceptionIfUserWithEmailIsAlreadySaved() {
        try (Handle h = dbi.open()) {
            h.execute("INSERT INTO " + userDao.TABLE_NAME + " (EMAIL) VALUES ('a@a.com')");
        }
        User user = new User("a@a.com", "passwd", "a");
        userDao.saveUser(user);
    }

    @Test
    public void findUser_shouldReturnNullIfNoUserFoundWithGivenEmail() {
        assertThat(userDao.findUser("a@a.com"), is(nullValue()));
    }

    @Test
    public void findUser_shouldReturnCorrectUser() {
        try (Handle h = dbi.open()) {
            h.execute("INSERT INTO " + userDao.TABLE_NAME + " (EMAIL, PASSWORD, DISPLAY_NAME) VALUES ('a@a.com', 'passwda', 'a')");
            h.execute("INSERT INTO " + userDao.TABLE_NAME + " (EMAIL, PASSWORD, DISPLAY_NAME) VALUES ('b@b.com', 'passwdb', 'b')");
        }
        assertThat(userDao.findUser("a@a.com"), is(new User("a@a.com", "passwda", "a")));
        assertThat(userDao.findUser("b@b.com"), is(new User("b@b.com", "passwdb", "b")));
    }

    @Test
    public void getAllUsers_shouldReturnEmptyListIfNoUsers() {
        assertThat(userDao.getAllUsers(), is(emptyList()));
    }

    @Test
    public void getAllUsers_shouldReturnListWithAllUsers() {
        try (Handle h = dbi.open()) {
            h.execute("INSERT INTO " + userDao.TABLE_NAME + " (EMAIL, PASSWORD, DISPLAY_NAME) VALUES ('a@a.com', 'passwda', 'a')");
            h.execute("INSERT INTO " + userDao.TABLE_NAME + " (EMAIL, PASSWORD, DISPLAY_NAME) VALUES ('b@b.com', 'passwdb', 'b')");
        }
        assertThat(userDao.getAllUsers(), containsInAnyOrder(
                new User("a@a.com", "passwda", "a"),
                new User("b@b.com", "passwdb", "b")
        ));
    }

    private User retrieveUser(String email) {
        try (Handle h = dbi.open()) {
            return h.createQuery("SELECT * FROM " + userDao.TABLE_NAME)
                    .map(userMapper)
                    .first();
        }
    }
}