package com.navii.server.persistence.dao;

import com.navii.server.persistence.domain.Tag;

import java.util.List;

/**
 * Created by stevejung on 2015-11-17.
 */
public interface TagDAO {

    List<Tag> findTags();
}
