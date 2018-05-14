package com.assignment.service;

import com.assignment.domain.model.UserModel;

import java.util.List;

/**
 * Created by wdy on 2/6/17.
 */
public interface UserService {

    List<UserModel> findAllUsers();

    void saveUser(UserModel user);

    Boolean existsByUserName(String userName);

    List<UserModel> listUserProfile(String userName);

}
