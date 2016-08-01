package com.navii.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.navii.server.persistence.domain.Voyager;
import com.navii.server.persistence.service.VoyagerService;
import com.navii.server.persistence.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ecrothers on 07/31/2016
 */
@ComponentScan(basePackages = {"com.navii.server"})
public class StatelessLoginFilter extends AbstractAuthenticationProcessingFilter {

    @Resource(name="voyagerServiceImpl")
    private VoyagerService voyagerService;

    @Autowired
    private TokenService authenticationService;

    public StatelessLoginFilter(String urlMapping) {
        super(new AntPathRequestMatcher(urlMapping));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        final Voyager voyager = new ObjectMapper().readValue(request.getInputStream(), Voyager.class);
        final UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(
                voyager.getUsername(), voyager.getPassword());
        return getAuthenticationManager().authenticate(loginToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authentication) throws IOException, ServletException {

        // Lookup the complete Voyager object from the database and create an Authentication for it
        final Voyager voyager = voyagerService.loadUserByUsername(authentication.getName());
        final UserAuth userAuthentication = new UserAuth(voyager);

        // Add the custom token as HTTP header to the response
        authenticationService.addAuthentication(response, userAuthentication);

        // Add the authentication to the Security context
        SecurityContextHolder.getContext().setAuthentication(userAuthentication);
    }
}