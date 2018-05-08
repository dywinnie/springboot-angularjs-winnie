package com.assignment.security.controller;

import com.assignment.domain.model.AuthorityModel;
import com.assignment.domain.model.AuthorityName;
import com.assignment.security.JwtTokenUtil;
import com.assignment.security.JwtUser;
import com.assignment.security.JwtUserFactory;
import com.assignment.security.service.JwtUserDetailsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.assignment.domain.model.UserModel;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRestControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @MockBean
    private JwtUserDetailsService jwtUserDetailsService;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void shouldGetUnauthorizedWithoutRole() throws Exception {

        mvc.perform(get("/is_user"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void getPersonsSuccessfullyWithUserRole() throws Exception {

        AuthorityModel authority = new AuthorityModel();
        authority.setId(1L);
        authority.setName(AuthorityName.ROLE_ADMIN);
        List<AuthorityModel> authorities = Arrays.asList(authority);

        UserModel user = new UserModel();
        user.setUserName("username");
        user.setAuthorities(authorities);
        user.setEnabled(Boolean.TRUE);
        user.setLastPasswordResetDate(new Date(System.currentTimeMillis() + 1000 * 1000));

        JwtUser jwtUser = JwtUserFactory.create(user);

        when(jwtTokenUtil.getUsernameFromToken(any())).thenReturn(user.getUserName());

        when(jwtUserDetailsService.loadUserByUsername(eq(user.getUserName()))).thenReturn(jwtUser);

        mvc.perform(get("/is_user").header("Authorization", "Bearer nsodunsodiuv"))
                .andExpect(status().is2xxSuccessful());
    }

}

