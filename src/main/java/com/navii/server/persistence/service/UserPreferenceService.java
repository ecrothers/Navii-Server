package com.navii.server.persistence.service;

import com.navii.server.persistence.domain.Preference;

import java.util.List;

/**
 * Created by sjung on 10/11/15.
 */
public interface UserPreferenceService {

    /**
     * Saves a new Preference entry to the database.
     *
     * @param saved The information of the saved Preference entry.
     * @return true if successful, false otherwise
     */
    boolean create(List<Preference> saved);

    /**
     * Return list of preferences based on the email
     *
     * @param username Username of the user
     * @return List of preferences
     */
    List<Preference> obtain(String username);

    /**
     * Deletes all userspreferences from the database based on the email
     *
     * @return Number of rows effected
     */
    int deleteAllPreference();
}
