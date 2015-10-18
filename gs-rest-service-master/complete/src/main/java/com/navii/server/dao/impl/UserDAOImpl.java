package com.navii.server.dao.impl;

import com.navii.server.dao.UserDAO;
import com.navii.server.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by JMtorii on 2015-10-15.
 */
@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    protected JdbcTemplate jdbc;

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findOne(int id) {
        return null;
    }

    @Override
    public User save(final User savedUser) {
        String insertString =
            "INSERT INTO Users (email, password, salt, isFacebook) VALUES (?, ?, ?, ?);";

        boolean success = jdbc.execute(insertString, new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setString(1, savedUser.getEmail());
                ps.setString(2, savedUser.getPassword());
                ps.setString(3, savedUser.getSalt());
                ps.setBoolean(4, savedUser.isFacebook());

                return ps.execute();
            }
        });

        return savedUser;
    }

    @Override
    public void delete(User deletedUser) {

    }
}
