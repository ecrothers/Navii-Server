package com.navii.server.persistence.dao;

import com.navii.server.persistence.domain.Preference;

import java.util.List;
import java.util.Set;

/**
 * Created by sjung on 08/12/15.
 */
public interface PreferenceDAO {

    /**
     * Finds all preference entries from the database based on preference type
     *
     * @param preferenceType type of preference in the database
     * @return All existing preferences based on the preference type.
     */
    List<Preference> getPreferences(int preferenceType);

    /**
     * Returns the question based on the preference type
     *
     * @param preferenceType preferenceType type of preference in the database
     * @return existing question found based on the preference type
     */
    String getQuestion(int preferenceType);

    /**
     * Returns the yelp categories based on user preferences
     *
     * @param userEmail email of the user
     *
     * @retun list of yelp categories corresponding to preferences
     */
    List<String> getYelpCategories(String userEmail);

    /**
     * Returns a set of categories to filter from yelp search
     * @return Set of categories to filter
     */
    Set<String> getYelpFilters();

}
