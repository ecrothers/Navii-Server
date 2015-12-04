package com.navii.server.persistence.service;

import java.util.List;

/**
 * Created by stevejung on 2015-11-17.
 */
public interface TagService {
    /**
     * Finds 20 random tag entries from the database.
     * @return  List of tags (20 max).
     */
    List<String> findTags();
}
