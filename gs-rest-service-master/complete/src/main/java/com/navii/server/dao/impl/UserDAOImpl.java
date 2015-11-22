package com.navii.server.dao.impl;

import com.navii.server.dao.UserDAO;
import com.navii.server.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;


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
    public ArrayList<User> findAll() {
        return null;
    }

    @Override
    public User findOne(final String username) {
        String selectString =
                "SELECT * FROM USER WHERE username = ?";
        boolean success = jdbc.execute(selectString, new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setString(1, username);

                return ps.execute();
            }
        });
        return null;
    }

    @Override
    public User findOne(final int id) {
        String selectString =
                "SELECT * FROM USER WHERE username = ?";
        boolean success = jdbc.execute(selectString, new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setInt(1, id);

                return ps.execute();
            }
        });
        return null;
    }

    @Override
    public User create(final User createdUser) {
        String insertString =
            "INSERT INTO users (username, saltedpassword, salt, isfacebook) VALUES (?, ?, ?, ?)";

        boolean success = jdbc.execute(insertString, new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setString(1, createdUser.getUsername());
                ps.setString(2, createdUser.getPassword());
                ps.setString(3, createdUser.getSalt());
                ps.setString(4, createdUser.isFacebook());

                return ps.execute();
            }
        });

        return createdUser;
    }

    @Override
    public User update(final User updatedUser) {
        return null;
    }

    @Override
    public User delete(final int deletedUser) {
        return null;
    }
}
