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
    public User findOne(int userId) {
        return null;
    }

    @Override
    public User create(final User savedUser) {
        String insertString =
            "INSERT INTO users (username, saltedpassword, salt, isfacebook) VALUES (?, ?, ?, ?)";

        boolean success = jdbc.execute(insertString, new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setString(1, savedUser.getUsername());
                ps.setString(2, savedUser.getPassword());
                ps.setString(3, savedUser.getSalt());
                ps.setString(4, savedUser.isFacebook());

                return ps.execute();
            }
        });

        return savedUser;
    }

    @Override
    public User update(User updatedUser) {
        return null;
    }

    @Override
    public User delete(int deletedUser) {
        return null;
    }
}
