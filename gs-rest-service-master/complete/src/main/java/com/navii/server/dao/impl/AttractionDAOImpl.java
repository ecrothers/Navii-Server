package com.navii.server.dao.impl;

import com.navii.server.dao.AttractionDAO;
import com.navii.server.domain.Attraction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

/**
 * Created by ecrothers on 2015-10-08.
 */
@Repository
public class AttractionDAOImpl implements AttractionDAO {
    @Autowired
    protected JdbcTemplate jdbc;

    private static final String TABLE_NAME = "Attractions";

    @Override
    public void delete(Attraction deleted) {
    }

    @Override
    public List<Attraction> findAll() {
        return null;
    }

    @Override
    public Attraction findOne(int id) {
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
    public Attraction save(final Attraction saved) {
        String insertString = "INSERT INTO " + TABLE_NAME +
                "(attractionid, name, location, photoURI, blurbURI, price, purchase, duration)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        boolean success = jdbc.execute(insertString, new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setInt(1, saved.getAttractionId());
                ps.setString(2, saved.getName());
                ps.setString(3, saved.getLocation());
                ps.setString(4, saved.getPhotoUri());
                ps.setString(5, saved.getBlurbUri());
                ps.setDouble(6, saved.getPrice());
                ps.setString(7, saved.getPurchase());
                ps.setInt(8, saved.getDuration());

                return ps.execute();
            }
        });

        return saved;
    }
}
