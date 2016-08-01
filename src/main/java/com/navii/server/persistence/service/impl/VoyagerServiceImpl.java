package com.navii.server.persistence.service.impl;

import com.navii.server.persistence.dao.VoyagerDAO;
import com.navii.server.persistence.domain.Voyager;
import com.navii.server.persistence.service.VoyagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by JMtorii on 2015-10-15.
 */
@Service
@SuppressWarnings("unused")
public class VoyagerServiceImpl implements VoyagerService {

    @Autowired
    VoyagerDAO voyagerDAO;

    @Override
    public List<Voyager> findAll() {
        return voyagerDAO.findAll();
    }

    @Override
    public Voyager findOne(String username) {
        return voyagerDAO.findOne(username);
    }

    @Override
    public Voyager findByEmail(String email) {
        return voyagerDAO.findByEmail(email);
    }

    @Override
    public Voyager loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByEmail(username);
    }
    @Override
    public int create(Voyager createdVoyager) {
        return voyagerDAO.create(createdVoyager);
    }

    @Override
    public int update(Voyager updatedVoyager, String newUsername) { return voyagerDAO.update(updatedVoyager, newUsername);}

    @Override
    public int updatePassword(String username, String oldPassword, String newPassword) {
        if (voyagerDAO.findOne(username).getPassword().equals(oldPassword)) {
            return voyagerDAO.updatePassword(username, newPassword);
        }

        return 0;
    }

    @Override
    public int delete(String username) {
        return voyagerDAO.delete(username);
    }

    @Override
    public void deleteAll() {
        voyagerDAO.deleteAll();
    }

    @Override
    public int signUp(String username, String password) {
        if (voyagerDAO.userExistsFromUsername(username)) {
            return 0;
        }

        // TODO: salt and set isFacebook properly
        Voyager voyager = new Voyager.Builder()
                .username(username)
                .password(password)
                .salt("!@#$%^&*()_+")
                .isFacebook(false)
                .build();

        return voyagerDAO.create(voyager);
    }

    @Override
    public boolean login(String username, String password) {
        return voyagerDAO.usernameAndPasswordMatch(username, password);
    }
}
