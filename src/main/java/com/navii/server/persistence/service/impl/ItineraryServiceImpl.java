package com.navii.server.persistence.service.impl;

import com.navii.server.persistence.dao.ItineraryDAO;
import com.navii.server.persistence.domain.Itinerary;
import com.navii.server.persistence.service.ItineraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ecrothers on 2015-10-08.
 */
@Service
@SuppressWarnings("unused")
public class ItineraryServiceImpl implements ItineraryService {
    @Autowired
    ItineraryDAO itineraryDAO;

    @Override
    public List<Itinerary> findAll() {
        return null;
    }

    @Override
    public Itinerary findOne(int id) {
        return null;
    }

    @Override
    public Itinerary save(Itinerary savedItinerary) {
        return itineraryDAO.save(savedItinerary);
    }

    @Override
    public void delete(Itinerary deletedItinerary) {

    }

    @Override
    public Itinerary update(Itinerary flock) {
        return null;
    }

    @Override
    public List<Itinerary> getItineraries(List<String> tagList) {
        return itineraryDAO.getItineraries(tagList);
    }
}
