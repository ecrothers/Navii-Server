package com.navii.server.persistence.dao.impl;

import com.navii.server.persistence.dao.UserDAO;
import com.navii.server.persistence.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
                        .id((int) row.get("userid"))
                        .username((String) row.get("username"))
                        .password((String) row.get("saltedPassword"))
                        .salt((String) row.get("salt"))
                        .isFacebook((String) row.get("isfacebook"))
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
                        "WHERE userid = ?;";

        try {
            return jdbc.queryForObject(sqlString, new Object[]{id}, new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {

                    if (rs.getRow() < 1) {
                        return null;
                    } else {
                        return new User.Builder(                        )
                            .id(rs.getInt("userid"))
                            .username(rs.getString("username"))
                            .password(rs.getString("saltedPassword"))
                            .salt(rs.getString("salt"))
                            .isFacebook(rs.getString("isfacebook"))
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
        String sqlString =
                "INSERT INTO users (username, saltedPassword, salt, isfacebook) " +
                        "VALUES (?, ?, ?, ?);";

        return jdbc.update(
                sqlString,
                createdUser.getUsername(),
                createdUser.getSaltedPassword(),
                createdUser.getSalt(),
                createdUser.isFacebook()
        );
    }

    @Override
    public int update(User updatedUser) {
        String sqlString =
                "UPDATE users " +
                        "SET username = ?, saltedPassword = ?, isfacebook = ?" +
                        "WHERE userid = ?";

        return jdbc.update(
                sqlString,
                updatedUser.getUsername(),
                updatedUser.getSaltedPassword(),
                updatedUser.isFacebook(),
                updatedUser.getId()
        );
    }

    @Override
    public int delete(int deletedUser) {
        String sqlString =
                "DELETE FROM users " +
                        "WHERE userid = ?";

        return jdbc.update(sqlString, deletedUser);
    }
}
