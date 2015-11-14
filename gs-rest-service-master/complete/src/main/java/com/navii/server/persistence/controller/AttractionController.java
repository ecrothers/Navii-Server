package com.navii.server.persistence.controller;

import com.navii.server.persistence.domain.Attraction;
import com.navii.server.persistence.service.AttractionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/attraction")
public class AttractionController {
    private static final Logger logger = LoggerFactory.getLogger(AttractionController.class);

    @Autowired
    private AttractionService attractionService;

    @RequestMapping(method=RequestMethod.POST)
    @ResponseBody
    public Attraction createAttraction(@RequestBody Attraction attraction) {
        Attraction created = attractionService.save(attraction);
        return created;
    }
}
