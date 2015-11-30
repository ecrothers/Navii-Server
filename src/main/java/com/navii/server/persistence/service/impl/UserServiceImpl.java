package com.navii.server.persistence.service.impl;

import com.navii.server.persistence.dao.UserDAO;
import com.navii.server.persistence.domain.User;
import com.navii.server.persistence.exception.UserException;
import com.navii.server.persistence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by JMtorii on 2015-10-15.
 */
@Service
@SuppressWarnings("unused")
public class UserServiceImpl implements UserService {

    @Autowired
    UserDAO userDAO;

    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    public User findOne(int userId) {
        return userDAO.findOne(userId);
    }

    @Override
    public int create(User createdUser) {
        return userDAO.create(createdUser);
    }

    @Override
    public int update(User updatedUser) {
        return userDAO.update(updatedUser);
    }

    @Override
    public int delete(int userId) {
        return userDAO.delete(userId);
    }

    @Override
    public int deleteAll() {
        return userDAO.deleteAll();
    }

    @Override
    public int signUp(String username, String password) throws UserException {
        if (userDAO.userExistsFromUsername(username)) {
            throw new UserException("User already exists.");
        }

        User user = new User.Builder()
                .username(username)
                .password(password)
                .salt("!@#$%^&*()_+")
                .isFacebook(false)
                .build();

        // TODO: use exception to check
        return userDAO.create(user);
    }
}
