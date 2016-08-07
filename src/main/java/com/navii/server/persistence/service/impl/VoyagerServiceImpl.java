package com.navii.server.persistence.service.impl;

import com.navii.server.UserAuth;
import com.navii.server.persistence.dao.VoyagerDAO;
import com.navii.server.persistence.domain.Voyager;
import com.navii.server.persistence.domain.VoyagerResponse;
import com.navii.server.persistence.service.LoginService;
import com.navii.server.persistence.service.TokenService;
import com.navii.server.persistence.service.VoyagerService;
import com.navii.server.persistence.service.util.HashingAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.List;

/**
 * Created by JMtorii on 2015-10-15.
 */
@Service
@SuppressWarnings("unused")
public class VoyagerServiceImpl implements VoyagerService {

    @Autowired
    private VoyagerDAO voyagerDAO;

    @Autowired
    @Qualifier("loginServiceImpl")
    private LoginService loginService;

    @Override
    public List<Voyager> findAll() {
        return voyagerDAO.findAll();
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
    public VoyagerResponse create(Voyager createdVoyager) {
        SecureRandom random = new SecureRandom();
        byte randomBytes[] = new byte[64];
        random.nextBytes(randomBytes);
        Voyager.Builder newVoyager = new Voyager.Builder().email(createdVoyager.getEmail())
                .username(createdVoyager.getUsername())
                .isFacebook(createdVoyager.isFacebook())
                .verified(false);

        try {
            String salt = HashingAlgorithm.sha256(randomBytes);
            byte[] saltBytes = DatatypeConverter.parseHexBinary(salt);
            byte[] passwordBytes = createdVoyager.getPassword().getBytes("UTF-8"); // UTF-8 or 16?
            byte[] saltedPasswordBytes = ByteBuffer.allocate(saltBytes.length + passwordBytes.length).put(saltBytes).put(passwordBytes).array();
            String password = HashingAlgorithm.sha256(saltedPasswordBytes);
            newVoyager.password(password);
            newVoyager.salt(salt);
            Voyager voyager = newVoyager.build();
            voyagerDAO.create(voyager);

        } catch(Exception e) {
            // TODO: Better handling than this
            return null;
        }

        return loginService.Login(createdVoyager.getEmail(), createdVoyager.getPassword());

    }

    @Override
    public int update(Voyager updatedVoyager, String newUsername) { return voyagerDAO.update(updatedVoyager, newUsername);}

    @Override
    public int updatePassword(String email, String oldPassword, String newPassword) {
        if (voyagerDAO.findByEmail(email).getPassword().equals(oldPassword)) {
            return voyagerDAO.updatePassword(email, newPassword);
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

    // TODO: This should probably be consolidated with "create"
    @Override
    public int signUp(String email, String username, String password) {
        if (voyagerDAO.userExistsFromEmail(email)) {
            return 0;
        }

        SecureRandom random = new SecureRandom();
        byte randomBytes[] = new byte[64];
        random.nextBytes(randomBytes);
        Voyager.Builder newVoyager = new Voyager.Builder().email(email)
                .username(username)
                .isFacebook(false) // TODO: fix
                .verified(false);

        try {
            String salt = HashingAlgorithm.sha256(randomBytes);
            byte[] saltBytes = DatatypeConverter.parseHexBinary(salt);
            byte[] passwordBytes = password.getBytes("UTF-8"); // UTF-8 or 16?
            byte[] saltedPasswordBytes = ByteBuffer.allocate(saltBytes.length + passwordBytes.length).put(saltBytes).put(passwordBytes).array();
            String finalPassword = HashingAlgorithm.sha256(saltedPasswordBytes);
            newVoyager.password(finalPassword);
            newVoyager.salt(salt);
            return voyagerDAO.create(newVoyager.build());
        } catch(Exception e) {
            // TODO: Better handling than this
            return -1;
        }
    }

    @Override
    public boolean login(String email, String password) {
        return voyagerDAO.emailAndPasswordMatch(email, password);
    }

    @Override
    public Voyager whoAmI() {
        UserAuth auth = (UserAuth) SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getDetails().getEmail();

        return voyagerDAO.findByEmail(email);
    }
}
