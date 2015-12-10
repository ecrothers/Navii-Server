package com.navii.server.persistence.service.impl;

import com.navii.server.persistence.dao.TagDAO;
import com.navii.server.persistence.domain.Tag;
import com.navii.server.persistence.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by stevejung on 2015-11-17.
 */
@Service
@SuppressWarnings("unused")
public class TagServiceImpl implements TagService {

    @Autowired
    TagDAO tagDAO;

    @Override
    public List<String> findTags() {
        return tagDAO.findTags();
    }

    @Override
    public int create(Tag tag) {
        return tagDAO.create(tag);
    }

    @Override
    public int deleteAll() {
        return tagDAO.deleteAll();
    }
}
