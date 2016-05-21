package com.navii.server.persistence.service.impl;

import com.navii.server.persistence.dao.ItineraryDAO;
import com.navii.server.persistence.dao.UserPreferenceDAO;
import com.navii.server.persistence.domain.*;
import com.navii.server.persistence.service.ItineraryService;
import com.navii.server.persistence.yelpAPI.YelpAPI;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
        List<String> preferenceList = new ArrayList<>();

        for (Preference preference : preferences)
                preferenceList.add(preference.getPreference().toLowerCase());

        // Initialize all possible venture variables
        Venture breakfast;
        Venture lunch;
        Venture dinner;
        Venture attraction1;
        Venture attraction2;
        Venture attraction3;

        // Initialize venture objects
        breakfast = new Venture(Venture.Type.MEAL, "Breakfast");
        lunch = new Venture(Venture.Type.MEAL, "Lunch");
        dinner = new Venture(Venture.Type.MEAL, "Dinner");

        attraction1 = new Venture(Venture.Type.ATTRACTION, "Landmark");
        attraction2 = new Venture(Venture.Type.ATTRACTION, "Landmark");
        attraction3 = new Venture(Venture.Type.ATTRACTION, "Landmark");

        // Modify venture objects based on preferences and tags
        if (preferenceList.contains("sophistica")) {
            attraction1.addCategory("arts");
            attraction2.addCategory("arts");
            attraction3.addCategory("arts");
        } else if (preferenceList.contains("hipster")) {
            attraction1.addCategory("arts");
            attraction2.addCategory("arts");
            attraction3.addCategory("arts");
        } else if (preferenceList.contains("adventure")) {
            attraction1.addCategory("active");
            attraction2.addCategory("active");
            attraction3.addCategory("active");
            attraction1.addCategory("nightlife");
            attraction2.addCategory("nightlife");
            attraction3.addCategory("nightlife");
            attraction1.addCategory("localflavor");
            attraction2.addCategory("localflavor");
            attraction3.addCategory("localflavor");
        } else if (preferenceList.contains("sporty")) {
            attraction1.addCategory("active");
            attraction2.addCategory("active");
            attraction3.addCategory("active");
        } else if (preferenceList.contains("slutty")) {
            attraction1.addCategory("adult");
            attraction2.addCategory("adult");
            attraction3.addCategory("adult");
            attraction1.addCategory("nightlife");
            attraction2.addCategory("nightlife");
            attraction3.addCategory("nightlife");
            attraction1.addCategory("beautysvc");
            attraction2.addCategory("beautysvc");
            attraction3.addCategory("beautysvc");
        } else if (preferenceList.contains("outdoor")) {
            attraction1.addCategory("active");
            attraction2.addCategory("active");
            attraction3.addCategory("active");
        } else if (preferenceList.contains("lazy")) {
            breakfast.addCategory("fooddeliveryservices");
            lunch.addCategory("fooddeliveryservices");
            dinner.addCategory("fooddeliveryservices");
            attraction1.addCategory("shopping");
            attraction2.addCategory("shopping");
            attraction3.addCategory("shopping");
        } else if (preferenceList.contains("foodie")) {
            attraction1.addCategory("food");
            attraction2.addCategory("food");
            attraction3.addCategory("food");
        } else if (preferenceList.contains("cultural")) {
            attraction1.addCategory("localflavor");
            attraction2.addCategory("localflavor");
            attraction3.addCategory("localflavor");
        } else if (preferenceList.contains("halal")) {
            breakfast.addCategory("halal");
            lunch.addCategory("halal");
            dinner.addCategory("halal");
        } else if (preferenceList.contains("gluten-free")) {
            breakfast.addCategory("gluten-free");
            lunch.addCategory("gluten-free");
            dinner.addCategory("gluten-free");
        } else if (preferenceList.contains("vegan")) {
            breakfast.addCategory("vegan");
            lunch.addCategory("vegan");
            dinner.addCategory("vegan");
        } else if (preferenceList.contains("vegetarian")) {
            breakfast.addCategory("vegetarian");
            lunch.addCategory("vegetarian");
            dinner.addCategory("vegetarian");
        }

        potentialAttractionStack.addAll(
                Arrays.asList(breakfast, attraction1, lunch, attraction2, attraction3, dinner));

        return potentialAttractionStack;
    }
}
