package com.navii.server.dao.impl;

import com.navii.server.dao.AttractionDAO;
import com.navii.server.domain.Attraction;

import java.sql.Connection;
import java.util.List;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by ecrothers on 2015-10-08.
 */
@Repository
public class AttractionDAOImpl implements AttractionDAO {
    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public void delete(Attraction deleted) {

    }

    @Override
    public List<Attraction> findAll() {
        return null;
    }

    // TODO: change to PreparedStatement
    // http://www.mkyong.com/spring/maven-spring-jdbc-example/
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
    public Attraction save(Attraction saved) {
        return null;
    }
}
