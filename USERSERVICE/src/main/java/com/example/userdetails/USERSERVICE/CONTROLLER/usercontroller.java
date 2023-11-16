package com.example.userdetails.USERSERVICE.CONTROLLER;


import java.net.URI;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.userdetails.USERSERVICE.dao.UserDaoService;
import com.example.userdetails.USERSERVICE.user.User;
import com.example.userdetails.USERSERVICE.user.UserNotFoundException;

import jakarta.validation.Valid;


@RestController
public class usercontroller {
	
	@Autowired// indicates that the constructor should be autowired when creating the bean
	private UserDaoService userdaoservice;
	
	
	//for all users
	@GetMapping("/users")
	public List<User> RetriveAllUser()
	{
		return userdaoservice.findAll();
	}
	
	//for 1 user
	
	@GetMapping("/users/{id}")
	public User RetriveUserById(@PathVariable int id)
	{
		User user=userdaoservice.findone(id);
		if(user==null)
		{
			throw new UserNotFoundException("id:"+id);
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
//	return ResponseEntity.status(HttpStatus.FOUND).body(user);
		return user;
	}
	
//DELETE user
	@DeleteMapping("/users/{id}")
	public void DeleteuserById(@PathVariable int id)
	{
		userdaoservice.deleteByid(id);
	
	}
	
	//POST users
	@PostMapping("/users")
	public ResponseEntity<User> CreateUser(@Valid @RequestBody User user)
	{
		User saved = userdaoservice.create(user);
		
		URI Location=ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(saved.getId())
				.toUri();///users/{id}
		return ResponseEntity.created(Location).build();
	}
	
//	//UPDATE user
//	@PutMapping("users/{id}")
//	public ResponseEntity<User> updateBook(@RequestBody User book,@PathVariable("id") int id)
//	{
//		
//		try{
//			this.userdaoservice.updateUser(book,id);
//			return ResponseEntity.ok().body(book);
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//		}
//		
//	}
}
