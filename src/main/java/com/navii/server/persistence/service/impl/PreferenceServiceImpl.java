package com.navii.server.persistence.service.impl;

import com.navii.server.persistence.dao.PreferenceDAO;
import com.navii.server.persistence.domain.Preference;
import com.navii.server.persistence.service.PreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by sjung on 10/11/15.
 */

@Service
@SuppressWarnings("unused")
public class PreferenceServiceImpl implements PreferenceService {

    @Autowired
    PreferenceDAO preferenceDAO;

    public Preference findOne(String tag) {
        return preferenceDAO.findOne(tag);
    }

    /**
     * Saves a new Preference entry to the database.
     * @param saved     The information of the saved Preference entry.
     * @return          The information fo the saved Preference entry.
     */
    public Preference save(Preference saved) {
        return preferenceDAO.save(saved);
    }
}
