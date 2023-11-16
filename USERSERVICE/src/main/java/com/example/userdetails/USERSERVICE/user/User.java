package com.example.userdetails.USERSERVICE.user;

import java.time.LocalDate;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Size(min=2,message="Name should have atleast 2 character")
	private String name;
	
	@Past(message="Birthdate should be in the past")
	private LocalDate dateofbirth;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getDateofbirth() {
		return dateofbirth;
	}
	public void setDateofbirth(LocalDate dateofbirth) {
		this.dateofbirth = dateofbirth;
	}
	public User(Integer id, String name, LocalDate dateofbirth) {
		super();
		this.id = id;
		this.name = name;
		this.dateofbirth = dateofbirth;
	}
	
	@Override
	public String toString() {
		return "UserData [id=" + id + ", name=" + name + ", dateofbirth=" + dateofbirth + "]";
	}
	

	
	
	

}
