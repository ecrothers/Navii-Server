package com.navii.server.persistence.service.impl;

import com.navii.server.persistence.dao.ActivityDAO;
import com.navii.server.persistence.domain.Activity;
import com.navii.server.persistence.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ecrothers on 2015-10-08.
 */
@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityDAO activityDAO;

    @Override
    public int delete(String activityId) {
        return activityDAO.delete(Integer.valueOf(activityId));
    }

    @Override
    public List<Activity> findAll() {
        return activityDAO.findAll();
    }

    @Override
    public Activity findOne(String activityId) {
        return activityDAO.findOne(Integer.valueOf(activityId));
    }

    @Override
    public int create(Activity saved) {
        return activityDAO.create(saved);
    }

    @Override
    public int update(String activityId, Activity updatedActivity) {
        return activityDAO.update(updatedActivity);
    }
}
