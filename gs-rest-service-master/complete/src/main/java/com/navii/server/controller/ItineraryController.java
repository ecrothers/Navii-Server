package com.navii.server.controller;

import com.navii.server.domain.Itinerary;
import com.navii.server.service.ItineraryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/itinerary")
public class ItineraryController {
    private static final Logger logger = LoggerFactory.getLogger(ItineraryController.class);

    @Autowired
    private ItineraryService itineraryService;

    @RequestMapping(value="/", method= RequestMethod.POST)
    public Itinerary createItinerary(@RequestBody Itinerary itinerary) {
        Itinerary created = itineraryService.save(itinerary);
        return created;
    }
}
