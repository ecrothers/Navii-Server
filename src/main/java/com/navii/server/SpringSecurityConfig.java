package com.navii.server;

/**
 * Created by Ni on 2015-11-16.
 */

import com.navii.server.persistence.service.VoyagerService;
import com.navii.server.persistence.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@Order(2)
@ComponentScan(basePackages = {"com.navii.server"})
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource(name= "voyagerServiceImpl")
    private VoyagerService voyagerService;

    @Autowired
    private TokenService tokenService;

    public SpringSecurityConfig() {
        super(true); // Fixes CSRF error
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    http
    .exceptionHandling().and()
    .anonymous().and()
    .servletApi().and()
    .headers().cacheControl();

    http
    .authorizeRequests()

    // Allow anonymous resource requests
    .antMatchers("/").permitAll()
    .antMatchers("/favicon.ico").permitAll()
    .antMatchers("**/*.html").permitAll()
    .antMatchers("**/*.css").permitAll()
    .antMatchers("**/*.js").permitAll()

    // Allow webtool traffic (take care to protect DB if this is enabled)
    // .antMatchers("/webtool/**").permitAll()
    // Allow anonymous logins
    .antMatchers("/login/**").permitAll()
    // Allow anonymous account creation
    .antMatchers("/user/**").permitAll()

    // All other request need to be authenticated
            .anyRequest().authenticated().and()

    // Create / pass Token upon login
//    .addFilterBefore(new StatelessLoginFilter(tokenService),
//            UsernamePasswordAuthenticationFilter.class)

    // Custom Token based authentication based on the header previously given to the client
            .addFilterBefore(new StatelessAuthenticationFilter(tokenService),
                    UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService()).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return voyagerService;
    }

    @Bean
    public TokenService tokenService() {
        return tokenService;
    }
}