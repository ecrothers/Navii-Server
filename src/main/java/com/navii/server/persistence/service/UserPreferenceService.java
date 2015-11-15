package com.navii.server.persistence.service;

import com.navii.server.persistence.domain.UserPreference;

/**
 * Created by sjung on 10/11/15.
 */
public interface UserPreferenceService {

    /**
     * Saves a new UserPreference entry to the database.
     * @param saved     The information of the saved UserPreference entry.
     * @return          The information fo the saved UserPreference entry.
     */
    UserPreference save(UserPreference saved);

    UserPreference obtain(String username);

}
