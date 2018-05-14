package com.assignment.service.impl;

import com.assignment.domain.model.AuthorityModel;
import com.assignment.domain.model.AuthorityName;
import com.assignment.domain.model.UserModel;
import com.assignment.domain.repository.AuthorityRepository;
import com.assignment.domain.repository.UserRepository;
import com.assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    private final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    @Autowired(required = true)
    private UserRepository userRepository;

    @Autowired(required = true)
    private AuthorityRepository authorityRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<UserModel> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<UserModel> listUserProfile(String userName) {
        UserModel user = userRepository.findByUserName(userName);
        List<UserModel> users = new ArrayList<>();
        users.add(user);
        return users;
    }

    @Override
    public void saveUser(UserModel user) {

        AuthorityModel authority = authorityRepository.findByName(AuthorityName.ROLE_USER);
        List<AuthorityModel> authorities = new ArrayList<>();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        authorities.add(authority);
        user.setAuthorities(authorities);
        user.setLastPasswordResetDate(new Date());
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public Boolean existsByUserName(final String userName) {
        return userRepository.existsByUserName(userName);
    }

}
