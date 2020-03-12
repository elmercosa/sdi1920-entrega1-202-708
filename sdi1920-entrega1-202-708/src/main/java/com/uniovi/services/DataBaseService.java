package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public void resetDataBase() {
		friendRepository.deleteAll();
		usersRepository.deleteAll();
		friendRequestRepository.deleteAll();
	}

}
