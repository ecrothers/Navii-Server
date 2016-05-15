package com.navii.server.persistence.service.impl;

import com.navii.server.persistence.dao.ItineraryDAO;
import com.navii.server.persistence.dao.UserPreferenceDAO;
import com.navii.server.persistence.domain.*;
import com.navii.server.persistence.service.ItineraryService;
import com.navii.server.persistence.yelpAPI.YelpAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ecrothers on 2015-10-08.
 */

@Service
public class ItineraryServiceImpl implements ItineraryService {
    @Autowired
    private ItineraryDAO itineraryDAO;

    @Autowired
    private UserPreferenceDAO userPreferenceDAO;

    @Autowired
    private YelpAPI yelpAPI;

    @Override
    public int delete(String itineraryId) {
        return itineraryDAO.delete(Integer.valueOf(itineraryId));
    }

    @Override
    public List<Itinerary> findAll() {
        return itineraryDAO.findAll();
    }

    @Override
    public Itinerary findOne(String itineraryId) {
        return itineraryDAO.findOne(Integer.valueOf(itineraryId));
    }

    @Override
    public int create(Itinerary saved) {
        return itineraryDAO.create(saved);
    }

    @Override
    public int update(String itineraryId, Itinerary updatedItinerary) {
        return itineraryDAO.update(updatedItinerary);
    }

    @Override
    public List<Itinerary> getItineraries(List<String> tagList) {
        List<Preference> preferences = userPreferenceDAO.obtain("username");

        List<Venture> potentialAttractionStack = buildPotentialAttractionStack(preferences, tagList);
        List<Itinerary> itineraryList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            List<Attraction> attractionList = yelpAPI.buildItinerary(potentialAttractionStack, i);
            Itinerary itinerary = new Itinerary.Builder()
                    .itineraryId(i)
                    .authorId("yelp")
                    .description("YelpAPI Generation " + i)
                    .attractions(attractionList)
                    .build();
            itineraryList.add(itinerary);
        }

        return itineraryList;
    }

    private List<Venture> buildPotentialAttractionStack(List<Preference> preferences, List<String> tags) {
        List<Venture> potentialAttractionStack = new ArrayList<>();

        //TODO: USE PREFERENCES AND TAGS
        // Initialize all possible venture variables
        Venture breakfast;
        Venture lunch;
        Venture dinner;
        Venture attraction1;
        Venture attraction2;
        Venture attraction3;

        List<String> categories = new ArrayList<>();
        categories.add("arts");
        categories.add("localflavor");
        // Based on the preferences initialize Venture object
        breakfast = new Venture(Venture.Type.MEAL, "Breakfast");
        lunch = new Venture(Venture.Type.MEAL, "Lunch");
        dinner = new Venture(Venture.Type.MEAL, "Dinner");
        attraction1 = new Venture(Venture.Type.ATTRACTION, "Park", "active");
        attraction2 = new Venture(Venture.Type.ATTRACTION, "Landmark");
        attraction3 = new Venture(Venture.Type.ATTRACTION, "Museum", categories );

        potentialAttractionStack.addAll(
                Arrays.asList(breakfast, attraction1, lunch, attraction2, attraction3, dinner));

        return potentialAttractionStack;
    }
}
