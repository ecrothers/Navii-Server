package com.navii.server.persistence.service;

/**
 * Created by JMtorii on 2015-10-12.
 */

public interface LoginService {
    /**
     * Authenticates user
     * @param email
     * @param password
     * @return SessionToken
     */
    String Login(String email, String password);

    /**
     * Authenticates user from Facebook
     * @param token
     * @return        SessionToken
     */
    String LoginFromFacebook(String token);
}
