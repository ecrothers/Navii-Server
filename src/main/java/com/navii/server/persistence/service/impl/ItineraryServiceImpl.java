package com.navii.server.persistence.service.impl;

import com.navii.server.persistence.dao.ItineraryDAO;
import com.navii.server.persistence.domain.*;
import com.navii.server.persistence.service.ItineraryService;
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
        return itineraryDAO.getItineraries(tagList);
    }

    private List<Venture> buildPotentialAttractionStack(List<Preference> preferences, List<Tag> tags) {
        List<Venture> potentialAttractionStack = new ArrayList<>();

        // Initialize all possible venture variables
        Venture breakfast;
        Venture lunch;
        Venture dinner;
        Venture attraction1;
        Venture attraction2;
        Venture attraction3;

        // Based on the preferences initialize Venture object
        breakfast = new Venture(Venture.Type.MEAL, "Breakfast");
        lunch = new Venture(Venture.Type.MEAL, "Lunch");
        dinner = new Venture(Venture.Type.MEAL, "Dinner");
        attraction1 = new Venture(Venture.Type.ATTRACTION, "Amusement Park");
        attraction2 = new Venture(Venture.Type.ATTRACTION, "Landmark");
        attraction3 = new Venture(Venture.Type.ATTRACTION, "Something else");

        potentialAttractionStack.addAll(
                Arrays.asList(breakfast, lunch, dinner, attraction1, attraction2, attraction3));

        return potentialAttractionStack;
    }

    private List<Attraction> buildAttractionList(List<Preference> preferences, List<Tag> tags) {
        List<Attraction> attractionList = new ArrayList<>();
        List<Venture> potentialAttractionStack = buildPotentialAttractionStack(preferences, tags);

        for (Venture venture : potentialAttractionStack) {
            // Call yelpAPI(venture) add to attractionList
        }

        return attractionList;
    }
}
