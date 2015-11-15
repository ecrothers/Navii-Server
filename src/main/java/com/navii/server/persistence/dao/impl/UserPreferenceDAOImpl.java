package com.navii.server.persistence.dao.impl;

import com.navii.server.persistence.dao.UserPreferenceDAO;
import com.navii.server.persistence.domain.Preference;
import com.navii.server.persistence.domain.UserPreference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by sjung on 10/11/15.
 */
@Repository
public class UserPreferenceDAOImpl implements UserPreferenceDAO {

    @Autowired
    protected JdbcTemplate jdbc;

    @Override
    public UserPreference save(final UserPreference saved) {
        String insertString =
                "INSERT INTO userspreferences (username, preference) VALUES (?, ?)";

        boolean success = jdbc.execute(insertString, new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setString(1, saved.getUsername());
                ps.setString(2, saved.getPreference());

                return ps.execute();
            }
        });

        return saved;
    }

    @Override
    public ArrayList<Preference> obtain(final String username) {
        String selectString =
                "SELECT preference FROM userspreferences WHERE username = ?";
        ArrayList<Preference> retrieved = jdbc.queryForObject(selectString,
                new String[]{username},
                new RowMapper<ArrayList<Preference>>() {
                    @Override
                    public ArrayList<Preference> mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ArrayList<Preference> preferences = new ArrayList<Preference>();
                        while(rs.next()) {
                            Preference preference = new Preference();
                            preference.setPreference(rs.getString("preference"));
                            preferences.add(preference);
                        }
                        return preferences;
                    }
                });
        return retrieved;
    }
}
