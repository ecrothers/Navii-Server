package com.navii.server.persistence.service.impl;

import com.navii.server.UserAuth;
import com.navii.server.persistence.domain.User;
import com.navii.server.persistence.service.TokenService;
import com.navii.server.persistence.service.UserService;
import com.navii.server.persistence.service.LoginService;
import com.navii.server.persistence.service.util.FacebookGraphResponse;
import com.navii.server.persistence.service.util.HashingAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.DatatypeConverter;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by JMtorii on 2015-10-12.
 */
@Service
@SuppressWarnings("unused")
public class LoginServiceImpl implements LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    private static final String FACEBOOK_GRAPH_API_ME = "https://graph.facebook.com/me?fields=name,email&access_token=";

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Override
    public String Login(String email, String password) {
        // TODO : Tighten up authentication?
        User user = userService.findOne(email);

        if (user == null) {
            return "";
        }

        String passwordAttempt = "";
        try {
            byte[] saltBytes = DatatypeConverter.parseHexBinary(user.getSalt());
            byte[] passwordBytes = password.getBytes("UTF-8"); // UTF-8 or 16?
            byte[] saltedPasswordBytes = ByteBuffer.allocate(saltBytes.length + passwordBytes.length).put(saltBytes).put(passwordBytes).array();
            passwordAttempt = HashingAlgorithm.sha256(saltedPasswordBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (passwordAttempt.equals(user.getPassword())) {
            logger.info("Authenticated password for " + user.getUsername());
            UserAuth auth = new UserAuth(user);
            return tokenService.getToken(auth);
        }

        return "";

    }

    @Override
    // TODO: Converted to email-only
    public String LoginFromFacebook(String token) {
        try {
            String url = FACEBOOK_GRAPH_API_ME + token;
            RestTemplate restTemplate = new RestTemplate();

            String fbName, fbEmail;
            try {
                FacebookGraphResponse response = restTemplate.getForObject(url, FacebookGraphResponse.class);
                fbName = response.getName();
                fbEmail = response.getEmail();
            } catch (Throwable ex) {
                throw new RuntimeException("FB request failure");
            }

            //TODO: Tighten access token failure cases? could get error:message from response for debug
            if (fbName == "" || fbEmail == "") {
                return "";
            }

            User user = userService.findOne(fbEmail);
            //TODO: Instead of a random password, possibly use a flag for fb login only accounts
            if (user == null) {
                User.Builder newFBUser = new User.Builder().username(fbEmail).isFacebook(true).verified(true);
                SecureRandom random = new SecureRandom();
                byte randomBytes[] = new byte[32];
                random.nextBytes(randomBytes);
                String randomPassword = HashingAlgorithm.sha256(randomBytes);
                newFBUser.password(randomPassword);

                userService.create(newFBUser.build());
                user = userService.findOne(fbEmail);
            }

            //if user is still null something happened during account creation and didn't throw
            if (user == null) {
                throw new RuntimeException("user creation from FB failed");
            }

            logger.info("Authenticated FB token for " + user.getUsername());
            UserAuth auth = new UserAuth(user);
            return tokenService.getToken(auth);

        } catch (Throwable ex) {
            throw new RuntimeException("failed login", ex);
        }
    };
}

