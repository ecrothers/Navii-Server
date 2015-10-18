package com.navii.server.controller;

import com.navii.server.domain.Attraction;
import com.navii.server.service.AttractionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/attraction")
public class ItineraryController {
    private static final Logger logger = LoggerFactory.getLogger(ItineraryController.class);
    private AttractionService attractionService;

    @Autowired
    ItineraryController(AttractionService attractionService) {
        this.attractionService = attractionService;
    }

    @RequestMapping(value="/", method= RequestMethod.POST)
    public Attraction createAttraction(@RequestBody Attraction attraction) {
        Attraction created = attractionService.save(attraction);
        return created;
    }
}
