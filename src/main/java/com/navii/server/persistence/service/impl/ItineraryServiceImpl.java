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

import java.util.*;
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
            yelpThreads[i].setName(YelpThread.getYelpName(i));
            yelpThreads[i].start();
        }

        List<Itinerary> itineraryList = new ArrayList<>();
        try {
            for (YelpThread thread : yelpThreads){
                thread.join();
                Itinerary itinerary = new Itinerary.Builder()
                        .description(thread.getName())
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

        Map<String, Integer> uniqueMap = new HashMap<>();
        List<String> categories = new ArrayList<>();
        List<String> foodCategories = new ArrayList<>();
        List<String> terms = new ArrayList<>();

        // Initialize venture objects
        breakfast = new Venture(Venture.Type.MEAL, "Breakfast");
        lunch = new Venture(Venture.Type.MEAL, "Lunch");
        dinner = new Venture(Venture.Type.MEAL, "Dinner");

        attraction1 = new Venture(Venture.Type.ATTRACTION, "Attraction");
        attraction2 = new Venture(Venture.Type.ATTRACTION, "Attraction");
        attraction3 = new Venture(Venture.Type.ATTRACTION, "Attraction");

        //TODO: Move to database because relational
        //OR CHANGE TO static map
        // Modify venture objects based on preferences and tags
        if (preferenceList.contains("sophisticated") || preferenceList.contains("hipster")) {
            categories.add("arts");
        } if (preferenceList.contains("adventure")) {
            categories.add("active");
            categories.add("nightlife");
            categories.add("localflavor");
        } if (preferenceList.contains("sporty")) {
            categories.add("active");
        } if (preferenceList.contains("adult")) {
            categories.add("adult");
            categories.add("nightlife");
            categories.add("beautysvc");
        } if (preferenceList.contains("outdoor")) {
            categories.add("active");
        } if (preferenceList.contains("lazy")) {
            foodCategories.add("fooddeliveryservices");
            categories.add("shopping");
        } if (preferenceList.contains("cultural")) {
            categories.add("localflavor");
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

        for (String tag : tags) {
            if (tag.equals("chinese") || tag.equals("japanese") || tag.equals("mexican")
                    || tag.equals("greek") || tag.equals("italian")) {
                breakfast.addCategory(tag);
                lunch.addCategory(tag);
                dinner.addCategory(tag);
            } else if (!tag.equals("indoor") || !tag.equals("hiking")){
                terms.add(tag);
            }
        }

        List<Venture> potentialAttractionStack = Arrays.asList(breakfast, attraction1, lunch, attraction2, attraction3, dinner);

        int size = (int) Math.ceil((double)categories.size()/3.0f);
        for (int i = 1; i < potentialAttractionStack.size(); i+=2 ) {
            for (int j = 0; j < size; j++) {
                String category = categories.remove(new Random().nextInt(categories.size()));
                potentialAttractionStack.get(i).addCategory(category);
            }
            potentialAttractionStack.get(i).setTerm(terms.remove(new Random().nextInt(terms.size())));
        }

        return potentialAttractionStack;
    }
}
