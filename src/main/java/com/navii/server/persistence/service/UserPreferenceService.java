package com.navii.server.persistence.service;

import com.navii.server.persistence.domain.Preference;
import com.navii.server.persistence.domain.UserPreference;

import java.util.List;

/**
 * Created by sjung on 10/11/15.
 */
public interface UserPreferenceService {

    /**
     * Saves a new UserPreference entry to the database.
     * @param saved     The information of the saved UserPreference entry.
     * @return          The information fo the saved UserPreference entry.
     */
    boolean create(UserPreference saved);

    List<Preference> obtain(String username);

    int deleteAllPreference(String username);
}
