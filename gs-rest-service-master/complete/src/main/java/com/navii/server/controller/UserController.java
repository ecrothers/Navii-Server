package com.navii.server.controller;

import com.navii.server.domain.User;
import com.navii.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by JMtorii on 2015-10-15.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value="/", method= RequestMethod.POST)
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }
}
