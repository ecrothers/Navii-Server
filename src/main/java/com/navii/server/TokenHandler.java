package com.navii.server;

import com.navii.server.persistence.domain.Voyager;
import com.navii.server.persistence.service.VoyagerService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * Created by ecrothers on 2016-07-24.
 */
@Component
@ComponentScan(basePackages = {"com.navii.server"})
public final class TokenHandler {

    private final String secret;

    @Autowired
    @Qualifier("voyagerServiceImpl")
    private VoyagerService voyagerService;

    private static final Logger logger = LoggerFactory.getLogger(TokenHandler.class);

    public TokenHandler() {
        super();
        this.secret = "someSecret";
    }

    public Voyager parseUserFromToken(String token) {
        String username = Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
        logger.info("Parsed user: " + username);
        return voyagerService.findOne(username);
    }

    public String createTokenForUser(Voyager voyager) {
        logger.info("Created token for voyager: " + voyager.getUsername());
        return Jwts.builder()
        .setSubject(voyager.getUsername())
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
    }
}