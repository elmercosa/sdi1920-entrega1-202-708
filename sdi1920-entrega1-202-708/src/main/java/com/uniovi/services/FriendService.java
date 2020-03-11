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
	
//	public Page<User> findAllForUser(Pageable pageable,User user){
//		List<Long> list = friendRequestRepository.findAllForUser(pageable, user.getId()).getContent();
//		List<User> listUsers = new LinkedList<User>();
//		for(Long id: list) {
//			if(usersRepository.findById(id).isPresent()) {
//				listUsers.add(usersRepository.findById(id).get());
//			}
//		}
//		Page<User> users = new PageImpl<User>(listUsers);
//		return users;
//	}
}
