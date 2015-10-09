package com.navii.server.controller;

import com.navii.server.domain.Attraction;
import com.navii.server.domain.Itinerary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping(value = "/navii")
public class AttractionController {
    static final Logger logger = LoggerFactory.getLogger(AttractionController.class);
    private final AtomicLong naviiCounter = new AtomicLong();

    // TODO: More than just one sample attraction
    Attraction localTestAttraction;

    @RequestMapping(value="/attraction/", method=RequestMethod.POST)
    public String createAttraction(@RequestBody Attraction navii) {
                            /*@RequestParam(value="name") String name,
                             @RequestParam(value="creatorId") long creatorId) {*/
        localTestAttraction = navii;
        long newId = localTestAttraction.getId();

        return "{\"Result\": \"OK\", \"AttractionId\": \"" + newId + "\"}"; // success
    }

    /*@RequestMapping(value="/itinerary/", method=RequestMethod.POST)
    public String createItinerary(@RequestBody Itinerary itin) {
        Itinerary userItinerary;
        userItinerary = itin;
        return "{\"Result\": \"OK\", \"ItineraryId\": \"" + userItinerary.getId() + "\"}"; // success
    }*/

    @RequestMapping(value="/attraction/", method=RequestMethod.GET)
    // TODO: Use a Response object
    public Attraction getAttraction(@RequestParam(value="id") long id) {
        // TODO
        if (localTestAttraction.getId() == id) {
            return localTestAttraction;
        } else {
            return null;
        }
    }

    /*@RequestMapping(value="/itinerary/", method=RequestMethod.GET) {
    public ClientItineraryPayload getItinerary(@RequestParam(value="id") long id) {
        // TODO
        if (localTestAttraction.getId() == id) {
            return localTestAttraction.getClientPayload(); // success
        } else {
            return null; // failure TODO: better error handling
        }
        return "not implemented";
    }*/
}
