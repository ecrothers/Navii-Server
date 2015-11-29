package com.navii.server.persistence.dao;

import com.navii.server.persistence.domain.Itinerary;
import com.navii.server.persistence.domain.ItineraryPreference;
import com.navii.server.persistence.domain.Preference;

import java.util.ArrayList;

/**
 * Created by sjung on 10/11/15.
 */
public interface ItineraryPreferenceDAO {

    ItineraryPreference save(ItineraryPreference itineraryPreference);

    Itinerary getItinerary(String preference);

    ArrayList<Itinerary> getItineraries(ArrayList<Preference> preferences);
}
