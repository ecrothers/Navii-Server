package com.navii.server.persistence.dao;

import java.util.List;

/**
 * Created by stevejung on 2015-11-17.
 */
public interface TagDAO {

    List<String> findTags();
}
