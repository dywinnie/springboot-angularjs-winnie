package com.assignment.service.impl;

import com.assignment.domain.model.AuthorityModel;
import com.assignment.domain.repository.AuthorityRepository;
import com.assignment.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by wdy on 5/13/18.
 */
@Service("authorityService")
@Transactional
public class AuthorityServiceImpl implements AuthorityService{
    private final Logger logger = Logger.getLogger(AuthorityServiceImpl.class.getName());

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public List<AuthorityModel> findAllAuthorities() {
        return authorityRepository.findAll();
    }
}
