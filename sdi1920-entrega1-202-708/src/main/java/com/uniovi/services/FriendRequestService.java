package com.uniovi.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.FriendRequests;
import com.uniovi.entities.User;
import com.uniovi.repositories.FriendRequestRepository;
import com.uniovi.repositories.UsersRepository;

@Service
public class FriendRequestService {
	@Autowired
	private FriendRequestRepository friendRequestRepository;
	
	@Autowired
	private UsersRepository usersRepository;

	public void sendFriendRequest(User from, User to) {
		friendRequestRepository.save(new FriendRequests(from.getId(), to.getId()));
	}
	
	public int searchFriendRequest(User from, User to) {
		return friendRequestRepository.findFriendRequest(from.getId(), to.getId());
	}
	
	public Page<User> findAllForUser(Pageable pageable,User user){
		List<Long> list = friendRequestRepository.findAllForUser(pageable, user.getId()).getContent();
		List<User> listUsers = new LinkedList<User>();
		for(Long id: list) {
			if(usersRepository.findById(id).isPresent()) {
				listUsers.add(usersRepository.findById(id).get());
			}
		}
		Page<User> users = new PageImpl<User>(listUsers);
		return users;
	}
	
	public void deleteFriendRequest(User from, User to) {
		friendRequestRepository.delete(friendRequestRepository.findFriendRequestFor(from.getId(), to.getId()));
	}
}
