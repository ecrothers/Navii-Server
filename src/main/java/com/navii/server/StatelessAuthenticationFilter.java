package com.navii.server;

import com.navii.server.persistence.service.TokenService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by ecrothers on 07/31/2016
 */

@ComponentScan(basePackages = {"com.navii.server"})
public class StatelessAuthenticationFilter extends GenericFilterBean {

    private final TokenService authenticationService;

    public StatelessAuthenticationFilter(TokenService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
        throws IOException, ServletException {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            //HttpServletResponse httpResponse = (HttpServletResponse) response;
            Authentication authentication = authenticationService.getAuthentication(httpRequest);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
            SecurityContextHolder.getContext().setAuthentication(null);
    }
}