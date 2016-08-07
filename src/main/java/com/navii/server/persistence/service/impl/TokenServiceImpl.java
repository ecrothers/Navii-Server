package com.navii.server.persistence.service.impl;

import com.navii.server.TokenHandler;
import com.navii.server.UserAuth;
import com.navii.server.persistence.domain.Voyager;
import com.navii.server.persistence.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ecrothers on 2016-07-24.
 */
@Service
public class TokenServiceImpl implements TokenService {
    private static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";

    @Autowired
    private TokenHandler tokenHandler;

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    @Override
    public void addAuthentication(HttpServletResponse response, UserAuth authentication) {
        final Voyager voyager = authentication.getDetails();
        response.addHeader(AUTH_HEADER_NAME, tokenHandler.createTokenForUser(voyager));
    }

    @Override
    public String getToken(UserAuth authentication) {
        final Voyager voyager = authentication.getDetails();
        return tokenHandler.createTokenForUser(voyager);
    }

    @Override
    public Authentication getAuthentication(HttpServletRequest request) {
        final String token = request.getHeader(AUTH_HEADER_NAME);
        if (token != null) {
            final Voyager voyager = tokenHandler.parseUserFromToken(token);
            if (voyager != null) {
                return new UserAuth(voyager);
            }
        }
        return null;
    }
}