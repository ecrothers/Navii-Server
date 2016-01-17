package com.navii.server.persistence.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.navii.server.persistence.dao.ItineraryDAO;
import com.navii.server.persistence.domain.Itinerary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * Created by ecrothers on 2015-10-08.
 */
@Repository
public class ItineraryDAOImpl implements ItineraryDAO {
    private static final Logger logger = LoggerFactory.getLogger(ItineraryDAOImpl.class);
    private static final String TABLE_NAME = "itineraries";

    // SQL column names
    private static final String SQL_ID = "itineraryid";
    private static final String SQL_COST = "totalcost";
    private static final String SQL_DESCRIPTION = "description";
    private static final String SQL_AUTHOR = "authorid";
    private static final String SQL_DURATION = "duration";

    @Autowired
    protected JdbcTemplate jdbc;

    @Override
    public List<Itinerary> findAll() {
        String query =
                "SELECT * FROM " + TABLE_NAME + ";";

        List<Itinerary> itinerarys = new ArrayList<Itinerary>();

        try {
            List<Map<String, Object>> rows = jdbc.queryForList(query);

            for (Map row : rows) {
                Itinerary itinerary = new Itinerary.Builder()
                        .itineraryId((int) row.get(SQL_ID))
                        .price((int) row.get(SQL_COST))
                        .description((String) row.get(SQL_DESCRIPTION))
                        .authorId((String) row.get(SQL_AUTHOR))
                        .duration((int) row.get(SQL_DURATION))
                        .build();

                itinerarys.add(itinerary);
            }

            return itinerarys;
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Itinerary: findAll returns no rows");
            return null;
        }
    }

    @Override
    public Itinerary findOne(final int itineraryId) {
        String query =
                "SELECT * FROM " + TABLE_NAME + " " +
                        "WHERE " + SQL_ID + " = ?;";

        try {
            return jdbc.queryForObject(query, new Object[]{itineraryId}, new RowMapper<Itinerary>() {
                @Override
                public Itinerary mapRow(ResultSet rs, int rowNum) throws SQLException {

                    if (rs.getRow() < 1) {
                        return null;
                    } else {
                        return new Itinerary.Builder()
                                .itineraryId(rs.getInt(SQL_ID))
                                .price(rs.getInt(SQL_COST))
                                .description(rs.getString(SQL_DESCRIPTION))
                                .authorId(rs.getString(SQL_AUTHOR))
                                .duration(rs.getInt(SQL_DURATION))
                                .build();
                    }
                }
            });
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Itinerary: findOne returns no rows");
            return null;
        }
    }

    @Override
    public int update(final Itinerary updated) {
        String query =
                "UPDATE " + TABLE_NAME + " " +
                        "SET " +
                        SQL_COST + "= ?, " +
                        SQL_DESCRIPTION + "= ?, " +
                        SQL_AUTHOR + "= ?, " +
                        SQL_DURATION + "= ?, " +
                        "WHERE " + SQL_ID + " = ?";

        return jdbc.update(query,
                updated.getPrice(),
                updated.getDescription(),
                updated.getAuthorId(),
                updated.getDuration(),
                updated.getItineraryId());
    }

    @Override
    public int delete(final int itineraryId) {
        String query =
                "DELETE FROM " + TABLE_NAME + " " +
                        "WHERE " + SQL_ID + " = ?";

        return jdbc.update(query, itineraryId);
    }

    @Override
    public int create(final Itinerary created) {
        System.out.println("Working?");
        String query = "INSERT INTO " + TABLE_NAME + " (" +
                SQL_COST + ", " +
                SQL_DESCRIPTION + ", " +
                SQL_AUTHOR + ", " +
                SQL_DURATION + ", " +
                ") VALUES (?, ?, ?, ?)";

        return jdbc.update(query,
                created.getPrice(),
                created.getDescription(),
                created.getAuthorId(),
                created.getDuration(),
                created.getItineraryId());
    }
}
