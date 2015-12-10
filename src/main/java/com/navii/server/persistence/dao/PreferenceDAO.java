package com.navii.server.persistence.dao;

import com.navii.server.persistence.domain.Preference;

import java.util.List;

/**
 * Created by sjung on 08/12/15.
 */
public interface PreferenceDAO {

    /**
     * Finds all preference entries from the database based on preference type
     * @param preferenceType type of preference in the database
     * @return  All existing preferences based on the preference type.
     */
    List<Preference> getPreferences(int preferenceType);
}
