package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.FriendRequests;
import com.uniovi.entities.User;
import com.uniovi.repositories.FriendRequestRepository;

@Service
public class FriendRequestService {
	@Autowired
	private FriendRequestRepository friendRequestRepository;

	public void sendFriendRequest(User from, User to) {
		friendRequestRepository.save(new FriendRequests(from.getId(), to.getId()));
	}
	
	public int searchFriendRequest(User from, User to) {
		return friendRequestRepository.findFriendRequest(from.getId(), to.getId());
	}
	
}
