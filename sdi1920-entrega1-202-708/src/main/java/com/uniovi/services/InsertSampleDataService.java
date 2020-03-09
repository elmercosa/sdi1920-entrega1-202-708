package com.uniovi.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;

public class InsertSampleDataService {
	@Autowired
	private UsersService usersService;
	@Autowired
	private RolesService rolesService;
	
	@PostConstruct
	public void init() {
		User admin = new User("Admin", "Admin", "admin@email.com", "admin");
		admin.setRole(rolesService.getRoles()[1]);
		usersService.addUser(admin);
		
		User user1 = new User("User1", "User1", "user1@email.com", "user1");
		user1.setRole(rolesService.getRoles()[0]);
		usersService.addUser(user1);
		
		User user2 = new User("User2", "User2", "user2@email.com", "user2");
		user2.setRole(rolesService.getRoles()[0]);
		usersService.addUser(user2);
		
		User user3 = new User("User3", "User3", "user3@email.com", "user3");
		user3.setRole(rolesService.getRoles()[0]);
		usersService.addUser(user3);
		
		User user4 = new User("User4", "User4", "user4@email.com", "user4");
		user4.setRole(rolesService.getRoles()[0]);
		usersService.addUser(user4);
	}

}