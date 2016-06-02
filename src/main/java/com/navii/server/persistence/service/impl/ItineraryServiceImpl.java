package com.navii.server.persistence.service.impl;

import com.navii.server.persistence.dao.ItineraryDAO;
import com.navii.server.persistence.dao.UserPreferenceDAO;
import com.navii.server.persistence.domain.Itinerary;
import com.navii.server.persistence.domain.Preference;
import com.navii.server.persistence.domain.Venture;
import com.navii.server.persistence.service.ItineraryService;
import com.navii.server.persistence.yelpAPI.YelpThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ecrothers on 2015-10-08.
 */

@Service
public class ItineraryServiceImpl implements ItineraryService {
    @Autowired
    private ItineraryDAO itineraryDAO;

    @Autowired
    private UserPreferenceDAO userPreferenceDAO;

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
        //TODO: GET PREFERENCE LIST FROM DB
        List<Preference> preferences = userPreferenceDAO.obtain("akhan");

        List<Venture> potentialAttractionStack = buildPotentialAttractionStack(preferences, tagList);
        YelpThread[] yelpThreads = new YelpThread[3];

        //Start and store the threads
        for (int i = 0; i < 3; i++) {
            yelpThreads[i] = new YelpThread(potentialAttractionStack, i, "Yelp " + i);
            yelpThreads[i].start();
        }

        List<Itinerary> itineraryList = new ArrayList<>();
        try {
            for (YelpThread thread : yelpThreads){
                thread.join();
                Itinerary itinerary = new Itinerary.Builder()
                        .description("Yelp Generation")
                        .authorId("Yelp")
                        .attractions(thread.getAttractions())
                        .build();
                itineraryList.add(itinerary);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return itineraryList;
    }

    private List<Venture> buildPotentialAttractionStack(List<Preference> preferences, List<String> tags) {
        List<Venture> potentialAttractionStack = new ArrayList<>();

        List<String> preferenceList =
                preferences.stream().map(preference -> preference.getPreference().toLowerCase()).collect(Collectors.toList());
        preferenceList.addAll(tags);
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

        //TODO: Move to database because relational
        //OR CHANGE TO static map
        // Modify venture objects based on preferences and tags
        if (preferenceList.contains("sophistica")) {
            attraction1.addCategory("arts");
            attraction2.addCategory("arts");
            attraction3.addCategory("arts");
        } if (preferenceList.contains("hipster")) {
            attraction1.addCategory("arts");
            attraction2.addCategory("arts");
            attraction3.addCategory("arts");
        } if (preferenceList.contains("adventure")) {
            attraction1.addCategory("active");
            attraction2.addCategory("active");
            attraction3.addCategory("active");
            attraction1.addCategory("nightlife");
            attraction2.addCategory("nightlife");
            attraction3.addCategory("nightlife");
            attraction1.addCategory("localflavor");
            attraction2.addCategory("localflavor");
            attraction3.addCategory("localflavor");
        } if (preferenceList.contains("sporty")) {
            attraction1.addCategory("active");
            attraction2.addCategory("active");
            attraction3.addCategory("active");
        } if (preferenceList.contains("slutty")) {
            attraction1.addCategory("adult");
            attraction2.addCategory("adult");
            attraction3.addCategory("adult");
            attraction1.addCategory("nightlife");
            attraction2.addCategory("nightlife");
            attraction3.addCategory("nightlife");
            attraction1.addCategory("beautysvc");
            attraction2.addCategory("beautysvc");
            attraction3.addCategory("beautysvc");
        } if (preferenceList.contains("outdoor")) {
            attraction1.addCategory("active");
            attraction2.addCategory("active");
            attraction3.addCategory("active");
        } if (preferenceList.contains("lazy")) {
            breakfast.addCategory("fooddeliveryservices");
            lunch.addCategory("fooddeliveryservices");
            dinner.addCategory("fooddeliveryservices");
            attraction1.addCategory("shopping");
            attraction2.addCategory("shopping");
            attraction3.addCategory("shopping");
        } if (preferenceList.contains("foodie")) {
//            attraction1.addCategory("food");
//            attraction2.addCategory("food");
//            attraction3.addCategory("food");
        } if (preferenceList.contains("cultural")) {
            attraction1.addCategory("localflavor");
            attraction2.addCategory("localflavor");
            attraction3.addCategory("localflavor");
        } if (preferenceList.contains("halal")) {
            breakfast.addCategory("halal");
            lunch.addCategory("halal");
            dinner.addCategory("halal");
        } if (preferenceList.contains("gluten-free")) {
            breakfast.addCategory("gluten-free");
            lunch.addCategory("gluten-free");
            dinner.addCategory("gluten-free");
        } if (preferenceList.contains("vegan")) {
            breakfast.addCategory("vegan");
            lunch.addCategory("vegan");
            dinner.addCategory("vegan");
        } if (preferenceList.contains("vegetarian")) {
            breakfast.addCategory("vegetarian");
            lunch.addCategory("vegetarian");
            dinner.addCategory("vegetarian");
        }


        potentialAttractionStack.addAll(
                Arrays.asList(breakfast, attraction1, lunch, attraction2, attraction3, dinner));

        return potentialAttractionStack;
    }
}
