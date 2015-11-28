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

    @Autowired
    protected JdbcTemplate jdbc;

    @Override
    public List<User> findAll() {
        String sqlString =
                "SELECT * FROM users;";

        List<User> users = new ArrayList<>();

        try {
            List<Map<String, Object>> rows = jdbc.queryForList(sqlString);

            for (Map row : rows) {
                User user = new User.Builder()
                        .userId((int) row.get("user_id"))
                        .username((String) row.get("username"))
                        .password((String) row.get("password"))
                        .salt((String) row.get("salt"))
                        .isFacebook((boolean) row.get("is_facebook"))
                        .build();

                users.add(user);
            }

            return users;
        } catch (EmptyResultDataAccessException e) {
            logger.warn("User: findAll returns no rows");
            return null;
        }
    }

    @Override
    public User findOne(int id) {
        String sqlString =
                "SELECT * FROM users " +
                        "WHERE user_id = ?;";

        try {
            return jdbc.queryForObject(sqlString, new Object[]{id}, new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {

                    if (rs.getRow() < 1) {
                        return null;
                    } else {
                        return new User.Builder(                        )
                            .userId(rs.getInt("user_id"))
                            .username(rs.getString("username"))
                            .password(rs.getString("password"))
                            .salt(rs.getString("salt"))
                            .isFacebook(rs.getBoolean("is_facebook"))
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
        try {
            String sqlString =
                    "INSERT INTO users (username, password, salt, is_facebook) " +
                            "VALUES (?, ?, ?, ?);";

            return jdbc.update(
                    sqlString,
                    createdUser.getUsername(),
                    createdUser.getPassword(),
                    createdUser.getSalt(),
                    createdUser.isFacebook()
            );
        } catch (DataAccessException e) {
            logger.warn("User: create returns no rows or contains an error");
            return 0;
        }

    }

    @Override
    public int update(User updatedUser) {
        String sqlString =
                "UPDATE users " +
                        "SET username = ?, password = ?, is_facebook = ?" +
                        "WHERE user_id = ?";

        return jdbc.update(
                sqlString,
                updatedUser.getUsername(),
                updatedUser.getPassword(),
                updatedUser.isFacebook(),
                updatedUser.getUserId()
        );
    }

    @Override
    public int delete(int deletedUser) {
        String sqlString =
                "DELETE FROM users " +
                        "WHERE user_id = ?";

        return jdbc.update(sqlString, deletedUser);
    }
}
