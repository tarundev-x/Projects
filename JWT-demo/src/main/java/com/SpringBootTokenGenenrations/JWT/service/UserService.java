package com.SpringBootTokenGenenrations.JWT.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//custom userservice to use spring security
@Service
public class UserService implements UserDetailsService {

	//HERE WE HAVE IMPLEMENTED THE USERDETAILSSERVICE  METHODS
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //Logic to get the user form the Database
    	//and we are converting the user to User class of Spring security and we are sending the data back

        return new User("admin","password",new ArrayList<>());
    }
}