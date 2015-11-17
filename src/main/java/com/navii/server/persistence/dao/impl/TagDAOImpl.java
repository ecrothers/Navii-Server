package com.navii.server.persistence.dao.impl;

import com.navii.server.persistence.dao.TagDAO;
import com.navii.server.persistence.domain.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by stevejung on 2015-11-17.
 */

@Repository
@SuppressWarnings("unused")
public class TagDAOImpl implements TagDAO {
    @Autowired
    protected JdbcTemplate jdbc;


    @Override
    public List<Tag> findTags() {
        String sqlString =
                "SELECT * FROM tags ORDER BY RAND() LIMIT 20;";

        List<Tag> users = new ArrayList<>();

        try {
            List<Map<String, Object>> rows = jdbc.queryForList(sqlString);

            for (Map row : rows) {
                Tag user = new Tag.Builder()
                        .tag((String) row.get("tag"))
                        .counter((int) row.get("counter"))
                        .build();

                users.add(user);
            }

            return users;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
