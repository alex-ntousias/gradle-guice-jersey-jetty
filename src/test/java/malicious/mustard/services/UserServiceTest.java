package malicious.mustard.services;

import malicious.mustard.repository.UserDao;
import malicious.mustard.transport.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserDao userDao;

    private UserService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        service = new UserService(userDao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createUser_shouldThrowExceptionIfNullEmail() {
        service.createUser(new User(null, "passwd", "display"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createUser_shouldThrowExceptionIfEmptyEmail() {
        service.createUser(new User("   ", "passwd", "display"));
    }

    @Test
    public void createUser_shouldSaveUserInDb() {
        User newUser = new User("user@somewhere.com", "passwd", "display");
        service.createUser(newUser);

        verify(userDao).saveUser(newUser);
        verifyNoMoreInteractions(userDao);
    }

    @Test
    public void getAllUsers_shouldNotReturnUsersPasswords() {
        List<User> users = Arrays.asList(
                new User("a@a.com", "passwd1", "a"),
                new User("b@b.com", "passwd2", "b")
        );
        when(userDao.getAllUsers()).thenReturn(users);

        List<User> usersWithoutPasswords = service.getAllUsers();
        assertThat(usersWithoutPasswords, contains(
                new User("a@a.com", null, "a"),
                new User("b@b.com", null, "b")
        ));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getUser_throwsExceptionIfNullEmail() {
        service.getUser(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getUser_throwsExceptionIfEmptyEmail() {
        service.getUser("    ");
    }

    @Test
    public void getUser_returnsUserIfThereIsOneWithGivenEmail() {
        User user1 = new User("user1@somewhere.com", "passwd1", "user1");
        User user2 = new User("user2@somewhere.com", "passwd2", "user2");

        when(userDao.findUser(user1.getEmail())).thenReturn(user1);
        when(userDao.findUser(user2.getEmail())).thenReturn(user2);

        assertThat(service.getUser(user1.getEmail()), is(user1));
        assertThat(service.getUser(user2.getEmail()), is(user2));
    }

}