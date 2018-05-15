package com.assignment.service;

import com.assignment.domain.model.UserModel;

import java.util.HashMap;
import java.util.List;

public interface UserService {

    List<HashMap<String, Object>> findAllUsers();

    void saveUser(UserModel user);

    UserModel saveUser(UserModel user, Long authorityID);

    void removeUser(UserModel user);

    Boolean existsByUserName(String userName);

    List<UserModel> listUserProfile(String userName);

}
