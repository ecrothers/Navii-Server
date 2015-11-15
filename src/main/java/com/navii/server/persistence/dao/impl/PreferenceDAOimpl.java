package com.navii.server.persistence.dao.impl;

import com.navii.server.persistence.dao.PreferenceDAO;
import com.navii.server.persistence.domain.Preference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sjung on 10/11/15.
 */

@Repository
public class PreferenceDAOimpl implements PreferenceDAO {
    @Autowired
    protected JdbcTemplate jdbc;

    @Override
    public Preference findOne(final String preference) {
        String selectString = "SELECT preference, counter, photourl FROM preferences WHERE preference = ?";
        Preference retrieved = jdbc.queryForObject(selectString,
                new String[]{preference},
                new RowMapper<Preference>() {
                    @Override
                    public Preference mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Preference pref = new Preference();
                        pref.setPreference(rs.getString("preference"));
                        pref.setCounter(rs.getInt("counter"));
                        pref.setPhotoUrl(rs.getString("photourl"));
                        return pref;
                    }
        });
        return retrieved;
    }

    @Override
    public Preference save(final Preference saved) {
        String insertString = "INSERT INTO preferences " +
                "(preference, photoURL)" +
                "VALUES (?, ?)";

        boolean success = jdbc.execute(insertString, new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setString(1, saved.getPreference());
                ps.setString(2, saved.getPhotoUrl());

                return ps.execute();
            }
        });

        return saved;
    }
}
