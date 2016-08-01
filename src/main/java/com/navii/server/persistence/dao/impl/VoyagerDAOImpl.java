package com.navii.server.persistence.dao.impl;

import com.navii.server.persistence.dao.VoyagerDAO;
import com.navii.server.persistence.domain.Voyager;
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
public class VoyagerDAOImpl implements VoyagerDAO {

    private static final Logger logger = LoggerFactory.getLogger(VoyagerDAOImpl.class);
    private static final String USERNAME_FIELD = "username";
    private static final String EMAIL_FIELD = "email";
    private static final String PASSWORD_FIELD = "password";
    private static final String SALT_FIELD = "salt";
    private static final String IS_FACEBOOK_FIELD = "is_facebook";
    private static final String VERIFIED_FIELD = "verified";

    @Autowired
    protected JdbcTemplate jdbc;

    @Override
    public List<Voyager> findAll() {
        final String sqlString =
                "SELECT * FROM users;";

        List<Voyager> voyagers = new ArrayList<>();
        List<Map<String, Object>> rows = jdbc.queryForList(sqlString);

        for (Map row : rows) {
            Voyager voyager = new Voyager.Builder()
                    .username((String) row.get(USERNAME_FIELD))
                    .email((String) row.get(EMAIL_FIELD))
                    .password((String) row.get(PASSWORD_FIELD))
                    .salt((String) row.get(SALT_FIELD))
                    .isFacebook((boolean) row.get(IS_FACEBOOK_FIELD))
                    .verified((boolean) row.get(VERIFIED_FIELD))
                    .build();

            voyagers.add(voyager);
        }

        return voyagers;
    }

    @Override
    public Voyager findOne(String username) {
        final String sqlString =
                "SELECT * FROM users " +
                        "WHERE username = ?;";

        try {
            return jdbc.queryForObject(sqlString, new Object[]{username}, new RowMapper<Voyager>() {
                @Override
                public Voyager mapRow(ResultSet rs, int rowNum) throws SQLException {

                    if (rs.getRow() < 1) {
                        return null;
                    } else {
                        return new Voyager.Builder()
                            .username(rs.getString(USERNAME_FIELD))
                            .email(rs.getString(EMAIL_FIELD))
                            .password(rs.getString(PASSWORD_FIELD))
                            .salt(rs.getString(SALT_FIELD))
                            .isFacebook(rs.getBoolean(IS_FACEBOOK_FIELD))
                            .verified(rs.getBoolean(VERIFIED_FIELD))
                            .build();
                    }
                }
            });
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Voyager: findOne returns no rows");
            return null;
        }
    }

    @Override
    public Voyager findByEmail(final String email) {
        String sqlString =
                "SELECT * FROM users " +
                        "WHERE email = ?;";

        try {
            return jdbc.queryForObject(sqlString, new Object[]{email}, new RowMapper<Voyager>() {
                @Override
                public Voyager mapRow(ResultSet rs, int rowNum) throws SQLException {

                    if (rs.getRow() < 1) {
                        return null;
                    } else {
                        return new Voyager.Builder()
                                .username(rs.getString("username"))
                                .email(rs.getString("email"))
                                .password(rs.getString("password"))
                                .isFacebook(rs.getBoolean("is_facebook"))
                                .verified(rs.getBoolean("verified"))
                                .salt(rs.getString("salt"))
                                .build();
                    }
                }
            });
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Goose: findByEmail returns no rows");
            return null;
        }
    }

    @Override
    public int create(Voyager createdVoyager) {
        final String sqlString =
                "INSERT INTO users (username, email, password, salt, is_facebook, verified) " +
                        "VALUES (?, ?, ?, ?, ?, ?);";

        try {
            return jdbc.update(
                    sqlString,
                    createdVoyager.getUsername(),
                    createdVoyager.getEmail(),
                    createdVoyager.getPassword(),
                    createdVoyager.getSalt(),
                    createdVoyager.isFacebook(),
                    createdVoyager.getVerified()
            );

        } catch (DataAccessException e) {
            logger.warn("Voyager: createUser returns no rows or contains an error");
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
            logger.warn("Voyager: updatePassword returns no rows or contains an error");
            return 0;
        }

    }

    @Override
    public int update(Voyager updatedVoyager, String newUsername) {
        final String sqlString =
                "UPDATE users " +
                        "SET username = ? " +
                        "WHERE username = ?";

        return jdbc.update(
                sqlString,
                newUsername,
                updatedVoyager.getUsername()
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
            logger.warn("Voyager: deleteUser returns no rows or contains an error");
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
