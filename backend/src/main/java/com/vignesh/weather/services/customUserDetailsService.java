package com.vignesh.weather.services;

import com.vignesh.weather.model.UserPrincipal;
import com.vignesh.weather.model.UsersModel;
import com.vignesh.weather.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class customUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsersModel user = repo.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("Username not found!!");
        }
        return new UserPrincipal(user);
    }
}
