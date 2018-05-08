package com.assignment.service.impl;

import com.assignment.domain.model.UserModel;
import com.assignment.domain.repository.UserRepository;
import com.assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by wdy on 2/6/17.
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    private final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    @Autowired(required = true)
    private UserRepository userRepository;

    @Override
    public List<UserModel> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void saveUser(UserModel user) {
        user.setLastPasswordResetDate(new Date());
        user.setEnabled(true);
        userRepository.save(user);
    }

}
