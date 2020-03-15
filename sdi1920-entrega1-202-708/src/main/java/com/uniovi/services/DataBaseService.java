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
		
		User user1 = new User("User1", "User1", "user1@email.com", "user1");
		user1.setRole("ROLE_USER");
		user1.setPassword(bCryptPasswordEncoder.encode(user1.getPassword()));
		usersRepository.save(user1);
		
		User user2 = new User("User2", "User2", "user2@email.com", "user2");
		user2.setRole("ROLE_USER");
		user2.setPassword(bCryptPasswordEncoder.encode(user2.getPassword()));
		usersRepository.save(user2);
		
		User user3 = new User("User3", "User3", "user3@email.com", "user3");
		user3.setRole("ROLE_USER");
		user3.setPassword(bCryptPasswordEncoder.encode(user3.getPassword()));
		usersRepository.save(user3);
		
		User user4 = new User("User4", "User4", "user4@email.com", "user4");
		user4.setRole("ROLE_USER");
		user4.setPassword(bCryptPasswordEncoder.encode(user4.getPassword()));
		usersRepository.save(user4);
	}

}
