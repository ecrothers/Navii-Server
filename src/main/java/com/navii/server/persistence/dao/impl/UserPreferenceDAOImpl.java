package com.navii.server.persistence.dao.impl;

import com.navii.server.persistence.dao.UserPreferenceDAO;
import com.navii.server.persistence.domain.Preference;
import com.navii.server.persistence.domain.UserPreference;
import org.springframework.beans.factory.annotation.Autowired;
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
    public boolean create(final UserPreference saved) {
        String insertString =
                "INSERT INTO userspreferences (username, preference) VALUES (?, ?)";
        List<Object[]> input = new ArrayList<>();

        for (String pref : saved.getPreferences()) {
            input.add(new Object[]{saved.getUsername(), pref});
        }
        try {
            int[] results = jdbc.batchUpdate(insertString, input);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;


    }

    @Override
    public List<Preference> obtain(final String username) {
        String selectString =
                "SELECT preference FROM userspreferences WHERE username = ?";
        ArrayList<Preference> retrieved = jdbc.queryForObject(selectString,
                new String[]{username},
                new RowMapper<ArrayList<Preference>>() {
                    @Override
                    public ArrayList<Preference> mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ArrayList<Preference> preferences = new ArrayList<>();
                        while (rs.next()) {
                            Preference preference = new Preference.Builder()
                                    .preference(rs.getString("preference"))
                                    .build();

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
        System.out.println(username);
        return jdbc.update(sqlString, username);

    }
}
