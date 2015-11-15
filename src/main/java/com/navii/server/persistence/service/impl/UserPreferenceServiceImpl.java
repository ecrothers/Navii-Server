package com.navii.server.persistence.service.impl;

import com.navii.server.persistence.dao.UserPreferenceDAO;
import com.navii.server.persistence.domain.UserPreference;
import com.navii.server.persistence.service.UserPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by sjung on 10/11/15.
 */
public class UserPreferenceServiceImpl implements UserPreferenceService {

    @Autowired
    UserPreferenceDAO userPreferenceDAO;

    @Override
    public UserPreference save(UserPreference saved) {
        return null;
    }

    @Override
    public UserPreference obtain(String username) {
        return null;
    }
}
