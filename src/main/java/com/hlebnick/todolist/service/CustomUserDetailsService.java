package com.hlebnick.todolist.service;

import com.hlebnick.todolist.storage.UsersDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger log = Logger.getLogger(CustomUserDetailsService.class);

    @Autowired
    private UsersDao usersDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDetails user;

        try {
            com.hlebnick.todolist.dao.User dbUser = usersDao.getUser(email);

            List<GrantedAuthority> authList = new ArrayList<>();
            authList.add(new SimpleGrantedAuthority("USER"));

            user = new User(
                    dbUser.getEmail(),
                    dbUser.getPassword(),
                    true,
                    true,
                    true,
                    true,
                    authList);

        } catch (Exception e) {
            log.error("Error in retrieving user", e);
            throw new UsernameNotFoundException("Error in retrieving user", e);
        }

        return user;
    }
}
