package com.navii.server.persistence.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.Statement;
import com.navii.server.persistence.dao.AttractionDAO;
import com.navii.server.persistence.domain.Attraction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 * Created by ecrothers on 2015-10-08.
 */
@Repository
public class AttractionDAOImpl implements AttractionDAO {
    private static final Logger logger = LoggerFactory.getLogger(AttractionDAOImpl.class);
    private static final String TABLE_NAME = "attractions";

    // SQL column names
    private static final String SQL_ID = "attractionid";
    private static final String SQL_NAME = "name";
    private static final String SQL_LOCATION = "location";
    private static final String SQL_PHOTO_URI = "photoURI";
    private static final String SQL_BLURB_URI = "blurbURI";
    private static final String SQL_PRICE = "price";
    private static final String SQL_PURCHASE = "purchase";
    private static final String SQL_DURATION = "duration";
    private static final String SQL_PHONENUMBER = "phone_number";
    private static final String SQL_RATING = "rating";
    private static final String SQL_DESCRIPTION = "description";
    private static final String SQL_LONGITUDE = "longitude";
    private static final String SQL_LATITUDE = "latitude";
    private static final String SQL_ADDRESS = "address";

    @Autowired
    protected JdbcTemplate jdbc;

    @Override
    public List<Attraction> findAll() {
        String query =
                "SELECT * FROM " + TABLE_NAME + ";";

        List<Attraction> attractions = new ArrayList<Attraction>();

        try {
            List<Map<String, Object>> rows = jdbc.queryForList(query);

            for (Map row : rows) {
                Attraction attraction = new Attraction.Builder()
                        .attractionId((int) row.get(SQL_ID))
                        .name((String) row.get(SQL_NAME))
                        .location((String) row.get(SQL_LOCATION))
                        .photoUri((String) row.get(SQL_PHOTO_URI))
                        .blurbUri((String) row.get(SQL_BLURB_URI))
                        .price((int) row.get(SQL_PRICE))
                        .purchase((String) row.get(SQL_PURCHASE))
                        .duration((int) row.get(SQL_DURATION))
                        .build();

                attractions.add(attraction);
            }

            return attractions;
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Attraction: findAll returns no rows");
            return null;
        }
    }

    @Override
    public Attraction findOne(final int attractionId) {
        String query =
                "SELECT * FROM " + TABLE_NAME + " " +
                        "WHERE " + SQL_ID + " = ?;";

        try {
            return jdbc.queryForObject(query, new Object[]{attractionId}, new RowMapper<Attraction>() {
                @Override
                public Attraction mapRow(ResultSet rs, int rowNum) throws SQLException {

                    if (rs.getRow() < 1) {
                        return null;
                    } else {
                        return new Attraction.Builder()
                                .attractionId(rs.getInt(SQL_ID))
                                .name(rs.getString(SQL_NAME))
                                .location(rs.getString(SQL_LOCATION))
                                .photoUri(rs.getString(SQL_PHOTO_URI))
                                .blurbUri(rs.getString(SQL_BLURB_URI))
                                .price(rs.getInt(SQL_PRICE))
                                .purchase(rs.getString(SQL_PURCHASE))
                                .duration(rs.getInt(SQL_DURATION))
                                .build();
                    }
                }
            });
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Attraction: findOne returns no rows");
            return null;
        }
    }

    @Override
    public int update(final Attraction updated) {
        String query =
                "UPDATE " + TABLE_NAME + " " +
                        "SET " +
                        SQL_NAME + "= ?, " +
                        SQL_LOCATION + "= ?, " +
                        SQL_PHOTO_URI + "= ?, " +
                        SQL_BLURB_URI + "= ?, " +
                        SQL_PRICE + "= ?, " +
                        SQL_PURCHASE + "= ?, " +
                        SQL_DURATION + "= ?, " +
                        "WHERE " + SQL_ID + " = ?";

        return jdbc.update(query,
                updated.getName(),
                updated.getLocation(),
                updated.getPhotoUri(),
                updated.getBlurbUri(),
                updated.getPrice(),
                updated.getPurchase(),
                updated.getDuration(),
                updated.getAttractionId());
    }

    @Override
    public int delete(final int attractionId) {
        String query =
                "DELETE FROM " + TABLE_NAME + " " +
                        "WHERE " + SQL_ID + " = ?";

        return jdbc.update(query, attractionId);
    }

    @Override
    public int create(final Attraction created) {
        String query = "INSERT INTO " + TABLE_NAME + " (" +
                SQL_NAME + ", " +
                SQL_LOCATION + ", " +
                SQL_PHOTO_URI + ", " +
                SQL_BLURB_URI + ", " +
                SQL_PRICE + ", " +
                SQL_PURCHASE + ", " +
                SQL_DURATION +
                ") VALUES (?, ?, ?, ?, ?, ?, ?)";

        return jdbc.update(query,
                created.getName(),
                created.getLocation(),
                created.getPhotoUri(),
                created.getBlurbUri(),
                created.getPrice(),
                created.getPurchase(),
                created.getDuration());
    }

    @Override
    public int createAttraction(Attraction create) {
        KeyHolder attractionKeyHolder = new GeneratedKeyHolder();
        String attractionQuery = "INSERT INTO " + TABLE_NAME + " (" +
                SQL_NAME + ", " +
                SQL_ADDRESS+ ", " +
                SQL_LATITUDE+ ", "+
                SQL_LONGITUDE+ ", "+
                SQL_DESCRIPTION+", "+
                SQL_RATING+", "+
                SQL_PHONENUMBER+", "+
                SQL_PHOTO_URI + ", " +
                SQL_PRICE + " " +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbc.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(attractionQuery, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, create.getName());
                ps.setString(2, create.getLocation().getAddress().toString());
                ps.setDouble(3, create.getLocation().getLatitude());
                ps.setDouble(4, create.getLocation().getLongitude());
                ps.setString(5, create.getDescription());
                ps.setDouble(6, create.getRating());
                ps.setString(7, create.getPhoneNumber());
                ps.setString(8, create.getPhotoUri());
                ps.setInt(9, create.getPrice());
                return ps;
            }
        }, attractionKeyHolder);
        return attractionKeyHolder.getKey().intValue();
    }
}
