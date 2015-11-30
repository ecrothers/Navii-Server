package com.navii.server.persistence.service.impl;

import com.navii.server.persistence.dao.AttractionDAO;
import com.navii.server.persistence.domain.Attraction;
import com.navii.server.persistence.service.AttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ecrothers on 2015-10-08.
 */
@Service
public class AttractionServiceImpl implements AttractionService {
    @Autowired
    private AttractionDAO attractionDAO;

    @Override
    public int delete(String attractionId) {
        return attractionDAO.delete(Integer.valueOf(attractionId));
    }

    @Override
    public List<Attraction> findAll() {
        return attractionDAO.findAll();
    }

    @Override
    public Attraction findOne(String attractionId) {
        return attractionDAO.findOne(Integer.valueOf(attractionId));
    }


    @Override
    public int create(Attraction saved) {
        return attractionDAO.create(saved);
    }

    @Override
    public int update(String attractionId, Attraction updatedAttraction) {
        return attractionDAO.update(updatedAttraction);
    }
}
