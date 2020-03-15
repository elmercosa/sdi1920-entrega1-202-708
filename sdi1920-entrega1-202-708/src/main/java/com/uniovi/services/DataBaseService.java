package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;
import com.uniovi.repositories.FriendRepository;
import com.uniovi.repositories.FriendRequestRepository;
import com.uniovi.repositories.UsersRepository;

@Service
public class DataBaseService {
	@Autowired
	private FriendRepository friendRepository;

	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private FriendRequestRepository friendRequestRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	 * Metodo que hace un reset de la base de datos y añade al admin automaticamente despues
	 */
	public void resetDataBase() {
		friendRepository.deleteAll();
		usersRepository.deleteAll();
		friendRequestRepository.deleteAll();
		User admin = new User("Admin", "Admin", "admin@email.com", "admin");
		admin.setRole("ROLE_ADMIN");
		admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
		usersRepository.save(admin);
	}

}
