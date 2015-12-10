package com.navii.server.persistence.service;

import com.navii.server.persistence.domain.Tag;

import java.util.List;

/**
 * Created by stevejung on 2015-11-17.
 */
public interface TagService {
    /**
     * Finds 20 random tag entries from the database.
     *
     * @return List of tags (20 max).
     */
    List<String> findTags();

    /**
     * Create a Tag object in the database
     *
     * @param tag Tag to enter into database
     * @return SQL code for function completion
     */
    int create(Tag tag);

    /**
     * Deletes all tags in the database
     *
     * @return Number of deleted tags
     */
    int deleteAll();
}

