package com.assignment.controller;

import com.assignment.domain.model.UserModel;
import com.assignment.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@WebAppConfiguration
public class UserRestApiController {

    public static final Logger logger = LoggerFactory.getLogger(UserRestApiController.class);

    @Autowired
    @Qualifier("userService")
    @Lazy
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserModel>> listAllUsers() {

        List<UserModel> users = userService.findAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<UserModel>>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/me", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> listUserProfile(@RequestBody UserModel userModel) {

        List<UserModel> users = userService.listUserProfile(userModel.getUserName());
        return new ResponseEntity<List<UserModel>>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createUser(@RequestBody UserModel user) {
        logger.info("Creating User : {}", user);
        if(userService.existsByUserName(user.getUserName())) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        userService.saveUser(user);
        return new ResponseEntity<UserModel>(user, HttpStatus.OK);
    }


}
