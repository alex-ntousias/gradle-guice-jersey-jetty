package malicious.mustard.repository.mappers;

import malicious.mustard.transport.User;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements ResultSetMapper<User> {

    @Override
    public User map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        User user = new User();
        user.setEmail(r.getString("email"));
        user.setPassword(r.getString("password"));
        user.setDisplayName(r.getString("display_name"));
        return user;
    }
}
