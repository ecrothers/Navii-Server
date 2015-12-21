package com.navii.server.persistence.service.impl;

import com.navii.server.persistence.dao.PreferenceDAO;
import com.navii.server.persistence.domain.Preference;
import com.navii.server.persistence.service.PreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sjung on 08/12/15.
 */
@Service
@SuppressWarnings("unused")
public class PreferenceServiceImpl implements PreferenceService{

    @Autowired
    private PreferenceDAO preferenceDAO;

    @Override
    public List<Preference> getPreferences(int preferenceType) {
        return preferenceDAO.getPreferences(preferenceType);
    }

    @Override
    public String getQuestion(int preferenceType) {
        return preferenceDAO.getQuestion(preferenceType);
    }
}
