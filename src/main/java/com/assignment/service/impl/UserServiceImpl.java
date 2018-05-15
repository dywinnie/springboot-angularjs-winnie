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
import java.util.HashMap;
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
    public List<HashMap<String, Object>> findAllUsers() {

        List<UserModel> users = userRepository.findAll();

        List<HashMap<String, Object>> usersMap = new ArrayList<HashMap<String, Object>>();
        for (UserModel user : users) {
            HashMap<String, Object> entity = new HashMap<String, Object>();
            entity.put("id", user.getId());
            entity.put("userName", user.getUserName());
            entity.put("firstName", user.getFirstName());
            entity.put("lastName", user.getLastName());
            entity.put("authority_id", user.getAuthorities().get(0).getId());
            entity.put("authority", user.getAuthorities().get(0).getName());
            entity.put("lastPasswordResetDate", user.getLastPasswordResetDate());
            usersMap.add(entity);
        }
        return usersMap;
    }

    @Override
    public void removeUser(UserModel user) {
        UserModel userModel = userRepository.findByUserName(user.getUserName());
        userRepository.delete(userModel);
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
    public UserModel saveUser(UserModel user, Long authorityId) {

        UserModel oldUser = userRepository.findByUserName(user.getUserName());
        user.setId(oldUser.getId());
        user.setPassword(oldUser.getPassword());
        user.setEnabled(oldUser.getEnabled());

        AuthorityModel authority = authorityRepository.findOne(authorityId);
        List<AuthorityModel> authorities = new ArrayList<>();
        authorities.add(authority);
        user.setAuthorities(authorities);
        userRepository.save(user);

        return userRepository.findByUserName(user.getUserName());
    }

    @Override
    public Boolean existsByUserName(final String userName) {
        return userRepository.existsByUserName(userName);
    }

}
