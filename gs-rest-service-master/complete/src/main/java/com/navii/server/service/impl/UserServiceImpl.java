package com.navii.server.service.impl;

import com.navii.server.dao.UserDAO;
import com.navii.server.domain.User;
import com.navii.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by JMtorii on 2015-10-15.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDAO userDAO;

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findOne(int id) {
        return null;
    }

    @Override
    public User save(User savedUser) {
        return userDAO.save(savedUser);
    }

    @Override
    public void delete(User deletedUser) {

    }
}
