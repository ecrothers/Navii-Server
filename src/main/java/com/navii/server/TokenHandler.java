package com.navii.server;

import com.navii.server.persistence.domain.User;
import com.navii.server.persistence.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by ecrothers on 2016-07-24.
 */
@Component
public final class TokenHandler {

    private final String secret;

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(TokenHandler.class);

    public TokenHandler() {
        super();
        this.secret = "someSecret";
    }

    public User parseUserFromToken(String token) {
        String username = Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
        logger.info("Parsed user: " + username);
        return userService.findOne(username);
    }

    public String createTokenForUser(User user) {
        logger.info("Created token for user: " + user.getUsername());
        return Jwts.builder()
        .setSubject(user.getUsername())
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
    }
}