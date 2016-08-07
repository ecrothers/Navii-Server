package com.navii.server.persistence.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.Statement;
import com.navii.server.UserAuth;
import com.navii.server.persistence.dao.AttractionDAO;
import com.navii.server.persistence.dao.ItineraryDAO;
import com.navii.server.persistence.domain.Attraction;
import com.navii.server.persistence.domain.Itinerary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    protected AttractionDAO attractionDAO;

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
    public List<Itinerary> getItineraries(List<String> tagList) {

        //TODO: Change to actual query
        String sqlString =
                "SELECT * FROM " +TABLE_NAME +
                        " ORDER BY RAND() " +
                        "LIMIT 3;";

        List<Itinerary> itineraries = new ArrayList<>();

        try {
            List<Map<String, Object>> rows = jdbc.queryForList(sqlString);

            for (Map row : rows) {
                Itinerary itinerary = new Itinerary.Builder()
                        .itineraryId((int)row.get(SQL_ID))
                        .authorId((String)row.get(SQL_AUTHOR))
                        .description((String)row.get(SQL_DESCRIPTION))
                        .duration((int)row.get(SQL_DURATION))
                        .build();
                itineraries.add(itinerary);
            }

        } catch (EmptyResultDataAccessException e) {
            logger.warn("None found.");
        }

        return itineraries;
    }

    @Override
    public int createList(List<Itinerary> itineraries) {
        String itineraryQuery = "INSERT INTO " + TABLE_NAME + " (" +
                SQL_AUTHOR + " " +
                ") VALUES (?)";
        String mapQuery = "INSERT INTO itineraries_days_attraction_positions (itineraryid, _day, _position, attractionid) VALUES (?, ?, ?, ?)";
        UserAuth auth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getDetails().getEmail();

        KeyHolder holder = new GeneratedKeyHolder();

        jdbc.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(itineraryQuery, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,userEmail);
                return ps;
            }
        }, holder);

        int itineraryId = holder.getKey().intValue();
        for (int day = 0; day < itineraries.size(); day++) {
            for (int position = 0; position < itineraries.get(day).getAttractions().size(); position++) {
                int attractionId = attractionDAO.createAttraction(itineraries.get(day).getAttractions().get(position));
                jdbc.update(mapQuery, itineraryId, day, position, attractionId);
            }
        }
        return 1;
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
        String query = "INSERT INTO " + TABLE_NAME + " (" +
                SQL_COST + ", " +
                SQL_DURATION + ", " +
                SQL_DESCRIPTION + ", " +
                SQL_AUTHOR + " " +
                ") VALUES (?, ?, ?, ?)";

        return jdbc.update(query,
                created.getPrice(),
                created.getDuration(),
                created.getDescription(),
                created.getAuthorId());
    }
}
