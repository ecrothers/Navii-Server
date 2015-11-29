package com.navii.server.persistence.dao.impl;

import com.navii.server.persistence.dao.ItineraryDAO;
import com.navii.server.persistence.domain.Itinerary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by ecrothers on 2015-10-08.
 */
@Repository
public class ItineraryDAOImpl implements ItineraryDAO {
    @Autowired
    protected JdbcTemplate jdbc;

    @Override
    public void delete(Itinerary deleted) {
    }

    @Override
    public List<Itinerary> findAll() {
        return null;
    }

    @Override
    public Itinerary findOne(int id) {
        Connection connection = null;

//        try {
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            if(connection != null ) {
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//
//                }
//            }
//        }

        return null;
    }

    @Override
    public Itinerary save(final Itinerary saved) {
        String insertString = "INSERT INTO itineraries " +
                "(totalcost, description, authorid, duration)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        boolean success = jdbc.execute(insertString, new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setDouble(1, saved.getPrice());
                ps.setString(2, saved.getDescription());
                ps.setInt(3, saved.getAuthorId());
                ps.setInt(4, saved.getDuration());
                //ps.setString(2, saved.getTags()); // TODO: Add to schema?

                return ps.execute();
            }
        });

        return saved;
    }
}
