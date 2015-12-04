package com.navii.server.persistence.dao.impl;

import com.navii.server.persistence.dao.UserPreferenceDAO;
import com.navii.server.persistence.domain.Preference;
import com.navii.server.persistence.domain.UserPreference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjung on 10/11/15.
 */
@Repository
public class UserPreferenceDAOImpl implements UserPreferenceDAO {

    @Autowired
    protected JdbcTemplate jdbc;

    @Override
    public boolean create(UserPreference saved) {
        String insertString =
                "INSERT INTO userspreferences (username, preference) VALUES (?, ?)";
        List<Object[]> input = new ArrayList<>();

        for (String pref : saved.getPreferences()) {
            input.add(new Object[]{saved.getUsername(), pref});
        }

        try {
            jdbc.batchUpdate(insertString, input);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public List<Preference> obtain(final String username) {
        String selectString =
                "SELECT preference FROM userspreferences WHERE username = ?";
        List<Preference> retrieved = jdbc.queryForObject(selectString,
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

    @Override
    public int deleteAllPreference(String username) {
        String sqlString =
                "DELETE FROM userspreferences " +
                        "WHERE username = ?";
        return jdbc.update(sqlString, username);

    }
}
