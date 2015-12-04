package com.navii.server.persistence.dao;

import java.util.List;

/**
 * Created by stevejung on 2015-11-17.
 */
public interface TagDAO {
    /**
     * Finds 20 random tag entries from the database.
     * @return  List of tags (20 max).
     */
    List<String> findTags();
}
