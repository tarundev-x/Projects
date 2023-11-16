package com.example.userdetails.USERSERVICE.dao;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.userdetails.USERSERVICE.user.User;

@Component//to detect custom beans in automatically
public class UserDaoService {
	
	private static Integer usercount=0;
	
	private static List<User> users=new ArrayList<>();
	static {
		users.add(new User(++usercount,"Dev",LocalDate.now().minusYears(30)));
		users.add(new User(++usercount,"Tarun",LocalDate.now().minusYears(32)));
		users.add(new User(++usercount,"Dev",LocalDate.now().minusYears(35)));
	}
	
	public List<User> findAll(){
		return users;
	}

	public User findone(int id) {
		
//		Predicate<? super User> Predicate = users->users.getId().equals(id);
		return  users.stream().filter(users->users.getId().equals(id)).findFirst().orElse(null);
		
	}

	//for creating new user//post
	public User create(User user)
	{
		user.setId(++usercount);
		users.add(user);
		return user;
	}

public void deleteByid(int id) {
		
//		Predicate<? super User> Predicate = users->users.getId().equals(id);
	users.removeIf(users->users.getId().equals(id));
		
	}

public void updateUser(User user, int id) {
	user.setId(++usercount);
	
	
	
}



	
}
	

