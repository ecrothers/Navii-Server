package com.navii.server.persistence.controller;

import com.navii.server.persistence.domain.Itinerary;
import com.navii.server.persistence.service.ItineraryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/itinerary")
public class ItineraryController {
    private static final Logger logger = LoggerFactory.getLogger(ItineraryController.class);

    @Autowired
    private ItineraryService itineraryService;

    @RequestMapping(method= RequestMethod.POST)
    public Itinerary createItinerary(@RequestBody Itinerary itinerary) {
        Itinerary created = itineraryService.save(itinerary);
        return created;
    }

    @RequestMapping(value="/tags" , method= RequestMethod.GET)
    public List<Itinerary> getItinerariesFromTags(@RequestBody List<String> tagList) {
        List<Itinerary> itineraries = itineraryService.getItineraries(tagList);
        return itineraries;
    }

}
