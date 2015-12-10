package com.navii.server.persistence.service;

import com.navii.server.persistence.domain.Preference;

import java.util.List;

/**
 * Created by sjung on 08/12/15.
 */
public interface PreferenceService {

    /**
     * Return a list of preferences based on type specified
     * @param preferenceType preference type
     * @return list of preferences
     */
    List<Preference> getPreferences(int preferenceType);
}
