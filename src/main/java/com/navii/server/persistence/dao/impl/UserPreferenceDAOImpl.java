package com.navii.server.persistence.dao.impl;

import com.navii.server.UserAuth;
import com.navii.server.persistence.dao.UserPreferenceDAO;
import com.navii.server.persistence.domain.Preference;
import com.navii.server.persistence.domain.UserPreference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private static final Logger logger = LoggerFactory.getLogger(UserPreferenceDAOImpl.class);

    @Autowired
    protected JdbcTemplate jdbc;

    @Override
    public boolean create(final List<Preference> saved) {
        String insertString =
                "INSERT INTO userspreferences (email, preference, preference_type) VALUES (?, ?, ?)";
        List<Object[]> input = new ArrayList<>();
        UserAuth auth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getDetails().getEmail();

        for (Preference preference : saved) {
            input.add(new Object[] {
                    userEmail,
                    preference.getPreference(),
                    preference.getPreferenceType()
            });
        }

        try {
            jdbc.batchUpdate(insertString, input);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    // TODO: move to Preference
    @Override
    public List<Preference> obtain(final String username) {
        String selectString =
                "SELECT preference FROM userspreferences WHERE email = ?";
        try {
            List<Preference> retrieved = jdbc.queryForObject(selectString,
                    new String[]{username},
                    new RowMapper<List<Preference>>() {
                        @Override
                        public List<Preference> mapRow(ResultSet rs, int rowNum) throws SQLException {
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

        } catch (EmptyResultDataAccessException e) {
            logger.error(e.toString());
            return new ArrayList<>();
        }
    }

    @Override
    public int deleteAllPreference(int preferenceType) {
        String sqlString =
                "DELETE FROM userspreferences " +
                        "WHERE email = ? AND preference_type = ?";

        UserAuth auth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getDetails().getEmail();

        return jdbc.update(sqlString, userEmail, preferenceType);
    }
}
