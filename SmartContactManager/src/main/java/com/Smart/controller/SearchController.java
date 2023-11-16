package com.Smart.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.Smart.dao.ContactRepository;
import com.Smart.dao.UserRepository;
import com.Smart.entities.Contact;
import com.Smart.entities.User;

@RestController
public class SearchController {
	@Autowired
	private UserRepository userepository;
	@Autowired
	private ContactRepository contactRepository;
	
	//search handler
	@GetMapping("/search/{query}")
	public ResponseEntity<?> search(@PathVariable("query") String query,Principal principal)
	{
		User user=this.userepository.getUserByByUserName(principal.getName());
		List<Contact> contacts = this.contactRepository.findByNameContainingAndUser(query, user);
		return ResponseEntity.ok(contacts);
	}

}
