package com.navii.server.persistence.service.impl;

import com.navii.server.persistence.dao.TagDAO;
import com.navii.server.persistence.domain.Tag;
import com.navii.server.persistence.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by stevejung on 2015-11-17.
 */
public class TagServiceImpl implements TagService {

    @Autowired
    TagDAO tagDAO;

    @Override
    public List<Tag> findTags() {
        return tagDAO.findTags();
    }
}
