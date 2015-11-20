package com.navii.server.persistence.dao;

import com.navii.server.persistence.domain.Preference;
import com.navii.server.persistence.domain.UserPreference;

import java.util.List;

/**
 * Created by sjung on 10/11/15.
 */
public interface UserPreferenceDAO {

    boolean create(UserPreference saved);

    List<Preference> obtain(String username);

    int deleteAllPreference(String userId);
}
