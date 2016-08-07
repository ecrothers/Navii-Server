package com.navii.server.persistence.service;

import com.navii.server.persistence.domain.VoyagerResponse;

/**
 * Created by JMtorii on 2015-10-12.
 */

public interface LoginService {
    /**
     * Authenticates user
     * @param email
     * @param password
     * @return Voyager object with token
     */
    VoyagerResponse Login(String email, String password);

    /**
     * Authenticates user from Facebook
     * @param token
     * @return        SessionToken
     */
    String LoginFromFacebook(String token);
}
