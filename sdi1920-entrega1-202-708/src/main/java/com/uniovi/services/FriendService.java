package com.uniovi.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Friends;
import com.uniovi.entities.User;
import com.uniovi.repositories.FriendRepository;
import com.uniovi.repositories.UsersRepository;

@Service
public class FriendService {
	@Autowired
	private FriendRepository friendRepository;
	
	@Autowired
	private UsersRepository usersRepository;

	public void addFriend(User from, User to) {
		if(friendRepository.findFriendship(from.getId(), to.getId()) == 0) {
			friendRepository.save(new Friends(from.getId(), to.getId()));
		}
	}

	public Page<User> findAllForUser(Pageable pageable,User user){
		Page<Friends> list = friendRepository.findFriendsForUser(pageable, user.getId());
		
		List<User> listUsers = new LinkedList<User>();
		
		for (Friends friends : list) {
			if(friends.getFriendId() == user.getId()) {
				listUsers.add(usersRepository.findById(friends.getPersonId()).get());
			}
			
			if(friends.getPersonId() == user.getId()) {
				listUsers.add(usersRepository.findById(friends.getFriendId()).get());
			}
		}
		Page<User> users = new PageImpl<User>(listUsers);
		return users;
	}
}
