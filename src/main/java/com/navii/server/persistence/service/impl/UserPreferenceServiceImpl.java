package com.navii.server.persistence.service.impl;

import com.navii.server.persistence.dao.UserPreferenceDAO;
import com.navii.server.persistence.domain.Preference;
import com.navii.server.persistence.service.UserPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sjung on 10/11/15.
 */

@Service
@SuppressWarnings("unused")
public class UserPreferenceServiceImpl implements UserPreferenceService {

    @Autowired
    UserPreferenceDAO userPreferenceDAO;

    @Override
    public boolean create(List<Preference> saved) {
        return userPreferenceDAO.create(saved);
    }

    @Override
    public List<Preference> obtain(String username) {
        return userPreferenceDAO.obtain(username);
    }

    @Override
    public int deleteAllPreference() {
        return userPreferenceDAO.deleteAllPreference();
    }
}
