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

	/**
	 * Metodo que añade una nueva amistad entre dos usuarios si no existe previamente
	 * @param from
	 * @param to
	 */
	public void addFriend(User from, User to) {
		if (friendRepository.findFriendship(from.getId(), to.getId()) == 0) {
			friendRepository.save(new Friends(from.getId(), to.getId()));
		}
	}
	
	/**
	 * Metodo que busca si el usuario actual de ssesion es amigo de otro usuario
	 * @param from
	 * @param to
	 */
	public boolean findFriendship(User from, User to) {
		return friendRepository.findFriendship(from.getId(), to.getId()) != 0;
	}

	/**
	 * Metodo que devuelve todos los amigos de un usuario, con paginacion
	 * @param pageable
	 * @param user
	 * @return
	 */
	public Page<User> findAllForUser(Pageable pageable, User user) {
		Page<Friends> list = friendRepository.findFriendsForUser(pageable, user.getId());

		List<User> listUsers = new LinkedList<User>();

		for (Friends friends : list) {
			if (friends.getFriendId() == user.getId()) {
				listUsers.add(usersRepository.findById(friends.getPersonId()).get());
			}

			if (friends.getPersonId() == user.getId()) {
				listUsers.add(usersRepository.findById(friends.getFriendId()).get());
			}
		}
		Page<User> users = new PageImpl<User>(listUsers);
		return users;
	}

	/**
	 * Metodo que devuelve todos los amigos de un usuario, utilizado para filtrar la lista de usuarios del sistema
	 * @param user
	 * @return
	 */
	public List<User> findAllFriendsForUser(User user) {
		List<Friends> list = friendRepository.findAllFriendsForUser(user.getId());

		List<User> listUsers = new LinkedList<User>();

		for (Friends friends : list) {
			if (friends.getFriendId() == user.getId()) {
				listUsers.add(usersRepository.findById(friends.getPersonId()).get());
			}

			if (friends.getPersonId() == user.getId()) {
				listUsers.add(usersRepository.findById(friends.getFriendId()).get());
			}
		}
		return listUsers;
	}
}
