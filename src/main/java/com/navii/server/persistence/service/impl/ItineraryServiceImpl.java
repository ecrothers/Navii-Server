package com.navii.server.persistence.service.impl;

import com.navii.server.UserAuth;
import com.navii.server.persistence.dao.ItineraryDAO;
import com.navii.server.persistence.dao.PreferenceDAO;
import com.navii.server.persistence.domain.*;
import com.navii.server.persistence.service.ItineraryService;
import com.navii.server.persistence.yelpAPI.YelpThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by ecrothers on 2015-10-08.
 */

@Service
public class ItineraryServiceImpl implements ItineraryService {
    @Autowired
    private ItineraryDAO itineraryDAO;

    @Autowired
    private PreferenceDAO preferenceDAO;

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
    public HeartAndSoulPackage getItineraries(List<String> tagList, int days) {
        //TODO: GET PREFERENCE LIST FROM DB
        UserAuth auth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getDetails().getEmail();
        List<String> yelpCategories = preferenceDAO.getYelpCategories(email);
        Set<String> filterCategories = preferenceDAO.getYelpFilters();
        List<String> foodCategories = new ArrayList<>();
        for (int i = tagList.size() - 1; i >= 0; i--) {
            String tag = tagList.get(i);
            if (tag.equals("chinese") || tag.equals("japanese") || tag.equals("mexican")
                    || tag.equals("greek") || tag.equals("italian")) {
                foodCategories.add(tag);
                tagList.remove(tag);
            }
        }
        List<Venture> potentialAttractionStack = buildPotentialAttractionStack(yelpCategories, foodCategories);
        YelpThread[] yelpThreads = new YelpThread[3];

        //Start and store the threads
        Set<Attraction> attractionsPrefetch = new HashSet<>();
        Set<Attraction> restaurantPrefetch = new HashSet<>();
        Set<String> uniqueNames = new HashSet<>();
        List<Itinerary> itineraries = new ArrayList<>();

        try {
            for (int i = 0; i < yelpThreads.length; i++) {
                String tag = "Yelp " + i;
                yelpThreads[i] = new YelpThread(potentialAttractionStack, days, tagList, i, tag, filterCategories);
                yelpThreads[i].setName(YelpThread.getYelpName(i));
                yelpThreads[i].start();
            }
            for (int i = 0; i < yelpThreads.length; i++) {
                YelpThread thread = yelpThreads[i];
                thread.join();
                itineraries.add(thread.getItinerary());

                for (Attraction attraction : thread.getAttractionsPrefetch()) {
                    if (!uniqueNames.contains(attraction.getName())) {
                        attractionsPrefetch.add(attraction);
                        uniqueNames.add(attraction.getName());
                    }
                }

                for (Attraction attraction : thread.getRestaurantPrefetch()) {
                    if (!uniqueNames.contains(attraction.getName())) {
                        restaurantPrefetch.add(attraction);
                        uniqueNames.add(attraction.getName());
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        HeartAndSoulPackage heartAndSoulPackage = new HeartAndSoulPackage.Builder()
                .itineraries(itineraries)
                .extraAttractions(new ArrayList<>(attractionsPrefetch))
                .extraRestaurants(new ArrayList<>(restaurantPrefetch))
                .build();

        return heartAndSoulPackage;
    }

    @Override
    public int createList(Itinerary itinerary, String title) {
        return itineraryDAO.createList(itinerary, title);
    }

    @Override
    public List<Itinerary> retrieveSavedItineraries() {
        return itineraryDAO.retrieveSavedItineraries();
    }

    private List<Venture> buildPotentialAttractionStack(List<String> categories, List<String> foodCategories) {
        // Initialize venture objects
        Venture restaurant = new Venture(Venture.Type.RESTAURANT, "Restaurant");
        // Set up List of search terms
        for (String category : foodCategories) {
            restaurant.addCategory(category);
        }

        Venture attraction = new Venture(Venture.Type.ATTRACTION);
        for (String category : categories) {
            if (category.equals("halal") || category.equals("gluten_free") || category.equals("vegan") || category.equals("vegetarian")) {
                restaurant.addCategory(category);
            } else {
                attraction.addCategory(category);
            }
        }

        return Arrays.asList(restaurant, attraction);
    }
}
