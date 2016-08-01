package com.navii.server.controller;

import com.navii.server.persistence.domain.Voyager;
import com.navii.server.persistence.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by JMtorii on 2015-10-12.
 */
@RestController
@RequestMapping(value = "/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<String> getToken(@RequestBody Voyager voyager) {
        String token = loginService.Login(voyager.getEmail(), voyager.getPassword());

        if (token != "") {
            return new ResponseEntity<>(token, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/fb/{accessToken}", method = RequestMethod.POST)
    public ResponseEntity<String> getTokenViaFB(@PathVariable String accessToken) {
        String token = loginService.LoginFromFacebook(accessToken);

        if (token != "") {
            return new ResponseEntity<>(token, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
