package evostar.dao;

import evostar.pojo.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO extends JdbcDaoSupport {
    public User getUserByUsername(String username) {
        assert this.getJdbcTemplate() != null;
        User user = this.getJdbcTemplate().queryForObject("select * from user where username = ?", new UserMapper(), username);
        return user;
    }

    class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setType(resultSet.getInt("type"));
            return user;
        }
    }
}
