package com.navii.server.service.impl;

import com.navii.server.dao.UserDAO;
import com.navii.server.domain.User;
import com.navii.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by JMtorii on 2015-10-15.
 */
@Service
@SuppressWarnings("unused")
public class UserServiceImpl implements UserService {

    @Autowired
    UserDAO userDAO;

    @Override
    public ArrayList<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    public User findOne(String userId) {
        return userDAO.findOne(Integer.valueOf(userId));
    }

    @Override
    public User create(User createdUser) {
        return userDAO.create(createdUser);
    }

    @Override
    public User update(String userId, User updatedUser) {
        // TODO: check whether userId matches updatedUser.userId
        return userDAO.update(updatedUser);
    }

    @Override
    public User delete(String userId) {
        return userDAO.delete(Integer.valueOf(userId));
    }
}
