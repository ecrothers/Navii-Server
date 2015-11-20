package com.navii.server.persistence.service;

import com.navii.server.persistence.domain.Preference;

/**
 * Created by sjung on 10/11/15.
 */
public interface PreferenceService {

    Preference findOne(String tag);

    /**
     * Saves a new Preference entry to the database.
     * @param saved     The information of the saved Preference entry.
     * @return          The information fo the saved Preference entry.
     */
    Preference save(Preference saved);
}
