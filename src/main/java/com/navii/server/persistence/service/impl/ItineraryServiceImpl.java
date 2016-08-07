package com.navii.server.persistence.service.impl;

import com.navii.server.UserAuth;
import com.navii.server.persistence.dao.ItineraryDAO;
import com.navii.server.persistence.dao.PreferenceDAO;
import com.navii.server.persistence.domain.Attraction;
import com.navii.server.persistence.domain.HeartAndSoulPackage;
import com.navii.server.persistence.domain.Itinerary;
import com.navii.server.persistence.domain.Venture;
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
        List<Venture> potentialAttractionStack = buildPotentialAttractionStack(yelpCategories, tagList);
        YelpThread[] yelpThreads = new YelpThread[3];

        //Start and store the threads
        Set<Attraction> attractionsPrefetch = new HashSet<>();
        Set<Attraction> restaurantPrefetch = new HashSet<>();

        List<Set<String>> uniqueCheckHashMap = new ArrayList<>();
        for (int i = 0; i < yelpThreads.length; i++) {
            uniqueCheckHashMap.add(new HashSet<>());
        }

        Itinerary[][] itineraries = new Itinerary[yelpThreads.length][days];
        for (int n = 0; n < days; n++) {
            try {
                for (int i = 0; i < yelpThreads.length; i++) {
                    yelpThreads[i] = new YelpThread(potentialAttractionStack, i, "Yelp " + i, attractionsPrefetch, restaurantPrefetch, uniqueCheckHashMap.get(i));
                    yelpThreads[i].setName(YelpThread.getYelpName(i));
                    yelpThreads[i].start();
                }
                Thread.sleep(100);
                for (int i = 0; i < yelpThreads.length; i++) {
                    YelpThread thread = yelpThreads[i];
                    thread.join();

                    itineraries[i][n] = new Itinerary.Builder()
                            .description(thread.getName())
                            .authorId("Yelp")
                            .attractions(thread.getAttractions())
                            .build();

                    uniqueCheckHashMap.get(i).addAll(thread.getUniqueCheckHashSet());
                    attractionsPrefetch.addAll(thread.getAttractionsPrefetch());
                    restaurantPrefetch.addAll(thread.getRestaurantPrefetch());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        HeartAndSoulPackage heartAndSoulPackage = new HeartAndSoulPackage.Builder()
                .itineraries(itineraries)
                .extraAttractions(new ArrayList<>(attractionsPrefetch))
                .extraRestaurants(new ArrayList<>(restaurantPrefetch))
                .build();

        return heartAndSoulPackage;
    }

    @Override
    public int createList(List<Itinerary> itineraries) {
        return itineraryDAO.createList(itineraries);
    }

    @Override
    public List<Itinerary> retrieveSavedItineraries() {
        return itineraryDAO.retrieveSavedItineraries();
    }

    private List<Venture> buildPotentialAttractionStack(List<String> categories, List<String> tags) {
        // Initialize venture objects
        Venture breakfast = new Venture(Venture.Type.RESTAURANT, "Restaurant");
        Venture lunch = new Venture(Venture.Type.RESTAURANT, "Restaurant");
        Venture dinner = new Venture(Venture.Type.RESTAURANT, "Restaurant");

        Venture attraction1 = new Venture(Venture.Type.ATTRACTION, "Attraction");
        Venture attraction2 = new Venture(Venture.Type.ATTRACTION, "Attraction");
        Venture attraction3 = new Venture(Venture.Type.ATTRACTION, "Attraction");

        // Set up List of search terms
        List<String> terms = new ArrayList<>();
        for (String tag : tags) {
            if (tag.equals("chinese") || tag.equals("japanese") || tag.equals("mexican")
                    || tag.equals("greek") || tag.equals("italian")) {
                breakfast.addCategory(tag);
                lunch.addCategory(tag);
                dinner.addCategory(tag);
            } else {
                terms.add(tag);
            }
        }
        if (categories.contains("halal")) {
            breakfast.addCategory("halal");
            lunch.addCategory("halal");
            dinner.addCategory("halal");
            categories.remove("halal");
        }
        if (categories.contains("gluten-free")) {
            breakfast.addCategory("gluten-free");
            lunch.addCategory("gluten-free");
            dinner.addCategory("gluten-free");
            categories.remove("gluten-free");
        }
        if (categories.contains("vegan")) {
            breakfast.addCategory("vegan");
            lunch.addCategory("vegan");
            dinner.addCategory("vegan");
            categories.remove("vegan");
        }
        if (categories.contains("vegetarian")) {
            breakfast.addCategory("vegetarian");
            lunch.addCategory("vegetarian");
            dinner.addCategory("vegetarian");
            categories.remove("vegetarian");
        }

        List<Venture> potentialAttractionStack = Arrays.asList(breakfast, attraction1, lunch, attraction2, attraction3, dinner);

        int size = (int) Math.ceil((double) categories.size() / 3.0f);
        for (int i = 1; i < potentialAttractionStack.size(); i += 2) {
            for (int j = 0; j < size; j++) {
                String category;
                if (categories.size() > 1) {
                    category = categories.remove(new Random().nextInt(categories.size()));
                } else {
                    category = categories.get(0);
                }
                potentialAttractionStack.get(i).addCategory(category);
            }
            if (terms.size() != 0) {
                potentialAttractionStack.get(i).setTerm(terms.remove(new Random().nextInt(terms.size())));
            }
        }

        return potentialAttractionStack;
    }
}
