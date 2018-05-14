package com.assignment.domain.repository;

import com.assignment.domain.model.AuthorityModel;
import com.assignment.domain.model.AuthorityName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by wdy on 5/13/18.
 */
@Repository
public interface AuthorityRepository extends JpaRepository<AuthorityModel, Long> {
    AuthorityModel findByName(AuthorityName authorityName);

}
