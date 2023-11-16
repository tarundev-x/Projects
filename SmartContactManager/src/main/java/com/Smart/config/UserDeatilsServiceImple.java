package com.Smart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.Smart.dao.UserRepository;
import com.Smart.entities.User;

public class UserDeatilsServiceImple implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		// fetching user from database

		User user = userRepository.getUserByByUserName(email);

		if (user == null) {
			throw new UsernameNotFoundException("Could not found user !!");
		}

		CustomUserDeatils customUserDeatils = new CustomUserDeatils(user);

		return customUserDeatils;
	}

}
