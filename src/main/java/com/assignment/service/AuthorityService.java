package com.assignment.service;

import com.assignment.domain.model.AuthorityModel;

import java.util.List;

/**
 * Created by wdy on 5/13/18.
 */
public interface AuthorityService {

    List<AuthorityModel> findAllAuthorities();
    
}
