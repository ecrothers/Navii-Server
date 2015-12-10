package com.navii.server.persistence.dao.impl;

import com.navii.server.persistence.dao.TagDAO;
import com.navii.server.persistence.domain.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(TagDAOImpl.class);

    @Autowired
    protected JdbcTemplate jdbc;

    @Override
    public List<String> findTags() {
        String sqlString =
                "SELECT * FROM tags " +
                        "ORDER BY RAND() " +
                        "LIMIT 20;";

        List<String> tags = new ArrayList<>();

        try {
            List<Map<String, Object>> rows = jdbc.queryForList(sqlString);

            for (Map row : rows) {
                String user = (String) row.get("tag");
                tags.add(user);
            }

        } catch (EmptyResultDataAccessException e) {
            logger.warn("Tags: None found.");
        }

        return tags;
    }

    @Override
    public int create(Tag tag) {
        String sqlQuery = "INSERT INTO tags (tag) VALUES (?)";
        return jdbc.update(sqlQuery, tag.getTag());
    }

    @Override
    public int deleteAll() {
        String setForeignKeyQuery = "SET FOREIGN_KEY_CHECKS = 0";
        String deleteQuery = "TRUNCATE TABLE tags";
        String resetForeignKeyQuery = "SET FOREIGN_KEY_CHECKS = 1";

        jdbc.update(setForeignKeyQuery);
        int returnCode = jdbc.update(deleteQuery);
        jdbc.update(resetForeignKeyQuery);

        return returnCode;
    }
}
