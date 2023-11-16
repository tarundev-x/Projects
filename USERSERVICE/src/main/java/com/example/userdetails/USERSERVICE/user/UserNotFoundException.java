package com.example.userdetails.USERSERVICE.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
//we defined here a user defined exception 
	public UserNotFoundException(String message)
	{
		super(message);
	}

	
}
