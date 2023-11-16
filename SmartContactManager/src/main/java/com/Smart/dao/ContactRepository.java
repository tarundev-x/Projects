package com.Smart.dao;

import java.util.List;

//import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Smart.entities.Contact;
import com.Smart.entities.User;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
	// pagination
	// current page-
	// contact perpage -5
	@Query("from Contact as c where c.user.id = :userId")
	public Page<Contact> findContactsByUser(@Param("userId") int userid, Pageable pageable);

	//search
	public List<Contact> findByNameContainingAndUser(String name,User user);
}
