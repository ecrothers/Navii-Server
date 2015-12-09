package com.navii.server.persistence.dao.impl;

import com.navii.server.persistence.dao.PreferenceDAO;
import com.navii.server.persistence.domain.Preference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjung on 08/12/15.
 */

@Repository
@SuppressWarnings("unused")
public class PreferenceDAOImpl implements PreferenceDAO {

    @Autowired
    protected JdbcTemplate jdbc;

    private static final String PARAM_PREFERENCE = "preference";
    private static final String PARAM_PREFERENCE_TYPE = "preference_type";
    private static final String PARAM_PHOTOURL = "photoURL";
    private static final String PARAM_COUNTER = "counter";

    @Override
    public List<Preference> getPreferences(int preferenceType) {
        try {
            String fetchQuery = "SELECT * FROM preferences WHERE preference_type = ?";
            List<Preference> retrieved = jdbc.queryForObject(fetchQuery,
                    new Object[]{preferenceType},
                    new RowMapper<ArrayList<Preference>>() {
                        @Override
                        public ArrayList<Preference> mapRow(ResultSet rs, int rowNum) throws SQLException {
                            ArrayList<Preference> preferences = new ArrayList<>();
                            while (rs.next()) {

                                Preference preference = new Preference.Builder()
                                        .preference(rs.getString(PARAM_PREFERENCE))
                                        .preferenceType(rs.getInt(PARAM_PREFERENCE_TYPE))
                                        .photoUrl(rs.getString(PARAM_PHOTOURL))
                                        .counter(rs.getInt(PARAM_COUNTER))
                                        .build();
                                preferences.add(preference);
                            }
                            return preferences;
                        }
                    });

            return retrieved;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

}
