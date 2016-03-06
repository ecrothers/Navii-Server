package com.navii.server.persistence.dao.impl;

import com.navii.server.persistence.dao.UserDAO;
import com.navii.server.persistence.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by JMtorii on 2015-10-15.
 */
@Repository
@SuppressWarnings("unused")
public class UserDAOImpl implements UserDAO {

    private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
    private static final String USERNAME_FIELD = "username";
    private static final String PASSWORD_FIELD = "password";
    private static final String SALT_FIELD = "salt";
    private static final String IS_FACEBOOK_FIELD = "is_facebook";

    @Autowired
    protected JdbcTemplate jdbc;

    @Override
    public List<User> findAll() {
        final String sqlString =
                "SELECT * FROM users;";

        List<User> users = new ArrayList<>();
        List<Map<String, Object>> rows = jdbc.queryForList(sqlString);

        for (Map row : rows) {
            User user = new User.Builder()
                    .username((String) row.get(USERNAME_FIELD))
                    .password((String) row.get(PASSWORD_FIELD))
                    .salt((String) row.get(SALT_FIELD))
                    .isFacebook((boolean) row.get(IS_FACEBOOK_FIELD))
                    .build();

            users.add(user);
        }

        return users;
    }

    @Override
    public User findOne(String username) {
        final String sqlString =
                "SELECT * FROM users " +
                        "WHERE username = ?;";

        try {
            return jdbc.queryForObject(sqlString, new Object[]{username}, new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {

                    if (rs.getRow() < 1) {
                        return null;
                    } else {
                        return new User.Builder()
                            .username(rs.getString(USERNAME_FIELD))
                            .password(rs.getString(PASSWORD_FIELD))
                            .salt(rs.getString(SALT_FIELD))
                            .isFacebook(rs.getBoolean(IS_FACEBOOK_FIELD))
                            .build();
                    }
                }
            });
        } catch (EmptyResultDataAccessException e) {
            logger.warn("User: findOne returns no rows");
            return null;
        }
    }

    @Override
    public int create(User createdUser) {
        final String sqlString =
                "INSERT INTO users (username, password, salt, is_facebook) " +
                        "VALUES (?, ?, ?, ?);";

        try {
            return jdbc.update(
                    sqlString,
                    createdUser.getUsername(),
                    createdUser.getPassword(),
                    createdUser.getSalt(),
                    createdUser.isFacebook()
            );

        } catch (DataAccessException e) {
            logger.warn("User: createUser returns no rows or contains an error");
            return 0;
        }
    }

    @Override
    public int updatePassword(String username, String password) {
        final String sqlString =
                "UPDATE users " +
                        "SET password = ? " +
                        "WHERE username = ?";
        try {
            return jdbc.update(
                    sqlString,
                    password,
                    username
            );
        } catch (DataAccessException e) {
            logger.warn("User: updatePassword returns no rows or contains an error");
            return 0;
        }

    }

    @Override
    public int update(User updatedUser) {
        final String sqlString =
                "UPDATE users " +
                        "SET username = ?, password = ?, is_facebook = ?" +
                        "WHERE username = ?";

        return jdbc.update(
                sqlString,
                updatedUser.getUsername(),
                updatedUser.getPassword(),
                updatedUser.isFacebook(),
                updatedUser.getUsername()
        );
    }

    @Override
    public int delete(String username) {
        final String sqlString =
                "DELETE FROM users " +
                        "WHERE username = ?";
        try {
            return jdbc.update(sqlString, username);

        } catch (DataAccessException e) {
            logger.warn("User: deleteUser returns no rows or contains an error");
            return 0;
        }

    }

    @Override
    public void deleteAll() {
        final String sqlString = "DELETE FROM users";

        jdbc.execute(sqlString);
    }

    @Override
    public boolean userExistsFromUsername(String username) {
        final String sqlString =
                "SELECT COUNT(*) FROM users " +
                        "WHERE username = ?;";

        Integer numUsers = jdbc.queryForObject(sqlString, new Object[]{username}, Integer.class);
        return numUsers != 0;
    }

    @Override
    public boolean usernameAndPasswordMatch(String username, String password) {
        final String sqlString =
                "SELECT COUNT(*) FROM users " +
                        "WHERE username = ? AND password = ?;";

        Integer numUsers = jdbc.queryForObject(sqlString, new Object[]{username, password}, Integer.class);
        return numUsers != 0;
    }
}
