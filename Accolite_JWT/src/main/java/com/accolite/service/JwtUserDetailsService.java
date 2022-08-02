package com.accolite.service;

import com.accolite.entity.AdminUser;
import com.accolite.repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Override
    public UserDetails loadUserByUsername(String adminUserNamePassword) throws UsernameNotFoundException {
        String[] cred = adminUserNamePassword.split(":");
        AdminUser adminUser = adminUserRepository.getAdminUserByCredentials(cred[0],cred[1]);
        return new User(adminUser.getAdminUserName(), adminUser.getAdminPassword(),
                new ArrayList<>());
    }

}
