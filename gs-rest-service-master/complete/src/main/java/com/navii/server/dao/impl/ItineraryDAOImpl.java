package com.navii.server.dao.impl;

import com.navii.server.dao.ItineraryDAO;
import com.navii.server.domain.Itinerary;
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
        String insertString = "INSERT INTO Itineraries " +
                "(ItineraryID, TotalCost, StartDate, EndDate, Tags, Description, AuthorID)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        boolean success = jdbc.execute(insertString, new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setInt(1, saved.getItineraryId());
                ps.setDouble(2, saved.getPrice());
                ps.setDate(3, saved.getStartDate());
                ps.setDate(4, saved.getEndDate());
                ps.setString(5, saved.getTags());
                ps.setString(6, saved.getDescription());
                ps.setDouble(7, saved.getAuthorId());

                return ps.execute();
            }
        });

        return saved;
    }
}
