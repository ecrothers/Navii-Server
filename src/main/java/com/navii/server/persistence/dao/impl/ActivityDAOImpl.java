package com.navii.server.persistence.dao.impl;

import com.navii.server.persistence.dao.ActivityDAO;
import com.navii.server.persistence.dao.util.DateTimeHelper;
import com.navii.server.persistence.domain.Activity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ecrothers on 2015-10-08.
 */
@Repository
public class ActivityDAOImpl implements ActivityDAO {
    private static final Logger logger = LoggerFactory.getLogger(ActivityDAOImpl.class);
    private static final String TABLE_NAME = "activities";

    // SQL column names
    private static final String SQL_ID = "activityid";
    private static final String SQL_START_TIME = "starttime";
    private static final String SQL_END_TIME = "endtime";
    private static final String SQL_ITINERARY_ID = "itineraryid";
    private static final String SQL_ATTRACTION_ID = "attractionid";

    @Autowired
    protected JdbcTemplate jdbc;

    @Override
    public List<Activity> findAll() {
        String query =
                "SELECT * FROM " + TABLE_NAME + ";";

        List<Activity> activities = new ArrayList<Activity>();

        try {
            List<Map<String, Object>> rows = jdbc.queryForList(query);

            for (Map row : rows) {
                Activity activity = new Activity.Builder()
                        .activityId((int) row.get(SQL_ID))
                        .build();

                activities.add(activity);
            }

            return activities;
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Activity: findAll returns no rows");
            return null;
        }
    }

    @Override
    public Activity findOne(final int activityId) {
        String query =
                "SELECT * FROM " + TABLE_NAME + " " +
                        "WHERE " + SQL_ID + " = ?;";

        try {
            return jdbc.queryForObject(query, new Object[]{activityId}, new RowMapper<Activity>() {
                @Override
                public Activity mapRow(ResultSet rs, int rowNum) throws SQLException {

                    if (rs.getRow() < 1) {
                        return null;
                    } else {
                        return new Activity.Builder()
                                .activityId(rs.getInt(SQL_ID))
                                .startTime(DateTimeHelper.fromDB(rs.getTimestamp("startTime")))
                                .endTime(DateTimeHelper.fromDB(rs.getTimestamp("endTime")))
                                .itineraryId(rs.getInt(SQL_ITINERARY_ID))
                                .attractionId(rs.getInt(SQL_ATTRACTION_ID))
                                .build();
                    }
                }
            });
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Activity: findOne returns no rows");
            return null;
        }
    }

    @Override
    public int update(final Activity updated) {
        String query =
                "UPDATE " + TABLE_NAME + " " +
                        "SET " +
                        SQL_ID + "= ?, " +
                        SQL_START_TIME + "= ?, " +
                        SQL_END_TIME + "= ?, " +
                        SQL_ITINERARY_ID + "= ?, " +
                        SQL_ATTRACTION_ID + "= ? " +
                        "WHERE " + SQL_ID + " = ?";

        return jdbc.update(query,
                updated.getActivityId(),
                updated.getStartTime(),
                updated.getEndTime(),
                updated.getItineraryId(),
                updated.getAttractionId());
    }

    @Override
    public int delete(final int activityId) {
        String query =
                "DELETE FROM " + TABLE_NAME + " " +
                        "WHERE " + SQL_ID + " = ?";

        return jdbc.update(query, activityId);
    }

    @Override
    public int create(final Activity created) {
        String query = "INSERT INTO " + TABLE_NAME + " (" +
                SQL_ID + ", " +
                SQL_START_TIME + ", " +
                SQL_END_TIME + ", " +
                SQL_ITINERARY_ID + ", " +
                SQL_ATTRACTION_ID + "" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?)";

        return jdbc.update(query,
                created.getActivityId(),
                created.getStartTime(),
                created.getEndTime(),
                created.getItineraryId(),
                created.getAttractionId());
    }
}
