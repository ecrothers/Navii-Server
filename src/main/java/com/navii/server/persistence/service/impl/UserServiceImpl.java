package com.navii.server.persistence.service.impl;

import com.navii.server.persistence.dao.UserDAO;
import com.navii.server.persistence.domain.User;
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
    public User findOne(String username) {
        return userDAO.findOne(username);
    }

    @Override
    public int create(User createdUser) {
        return userDAO.create(createdUser);
    }

    @Override
    public int update(User updatedUser, String newUsername) { return userDAO.update(updatedUser, newUsername);}

    @Override
    public int updatePassword(String username, String oldPassword, String newPassword) {
        if (userDAO.findOne(username).getPassword().equals(oldPassword)) {
            return userDAO.updatePassword(username, newPassword);
        }

        return 0;
    }

    @Override
    public int delete(String username) {
        return userDAO.delete(username);
    }

    @Override
    public void deleteAll() {
        userDAO.deleteAll();
    }

    @Override
    public int signUp(String username, String password) {
        if (userDAO.userExistsFromUsername(username)) {
            return 0;
        }

        // TODO: salt and set isFacebook properly
        User user = new User.Builder()
                .username(username)
                .password(password)
                .salt("!@#$%^&*()_+")
                .isFacebook(false)
                .build();

        return userDAO.create(user);
    }

    @Override
    public boolean login(String username, String password) {
        return userDAO.usernameAndPasswordMatch(username, password);
    }
}
