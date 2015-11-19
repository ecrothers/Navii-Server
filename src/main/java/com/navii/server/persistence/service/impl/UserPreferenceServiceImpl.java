package com.navii.server.persistence.service.impl;

import com.navii.server.persistence.dao.UserPreferenceDAO;
import com.navii.server.persistence.domain.Preference;
import com.navii.server.persistence.domain.UserPreference;
import com.navii.server.persistence.service.UserPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by sjung on 10/11/15.
 */

@Service
@SuppressWarnings("unused")
public class UserPreferenceServiceImpl implements UserPreferenceService {

    @Autowired
    UserPreferenceDAO userPreferenceDAO;

    @Override
    public boolean create(UserPreference saved) {
        return userPreferenceDAO.create(saved);
    }

    @Override
    public ArrayList<Preference> obtain(String username) {
        return userPreferenceDAO.obtain(username);
    }

    @Override
    public int deleteAllPreference(String userId) {
        return userPreferenceDAO.deleteAllPreference(userId);
    }
}
