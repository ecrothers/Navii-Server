package com.navii.server.service.impl;

import com.navii.server.dao.AttractionDAO;
import com.navii.server.domain.Attraction;
import com.navii.server.service.AttractionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by ecrothers on 2015-10-08.
 */
public class AttractionServiceImpl implements AttractionService {
    @Autowired
    AttractionDAO attractionDAO;

    @Override
    public List<Attraction> findAll() {
        return null;
    }

    @Override
    public Attraction findOne(int id) {
        return null;
    }

    @Override
    public Attraction save(Attraction savedAttraction) {
        return attractionDAO.save(savedAttraction);
    }

    @Override
    public void delete(Attraction deletedAttraction) {

    }

    @Override
    public Attraction update(Attraction flock) {
        return null;
    }
}
