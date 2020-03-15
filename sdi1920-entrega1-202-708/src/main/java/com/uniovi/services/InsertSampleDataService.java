package com.uniovi.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;

//@Service
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
	}

}