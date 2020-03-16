package com.uniovi.services;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;
import com.uniovi.repositories.UsersRepository;

@Service
public class UsersService {
	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	/**
	 * Meodo que devuelve todos los usuarios del sistema, con paginacion
	 * @param pageable
	 * @param user
	 * @return
	 */
	public Page<User> getUsers(Pageable pageable, User user) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		users = usersRepository.listUsers(pageable,user);
		return users;
	}
	
	/**
	 * Meodo que devuelve todos los usuarios del sistema, con paginacion, version ADMIN
	 * @param pageable
	 * @param user
	 * @return
	 */
	public Page<User> getUsersAdmin(Pageable pageable) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		users = usersRepository.listUsersAdmin(pageable);
		return users;
	}
	
	/**
	 * Metodo que busca a los usuarios del sistema que contengan una cadena pasada por parametro en su nombre, apellido o email.
	 * @param pageable
	 * @param user
	 * @param searchText
	 * @return
	 */
	public Page<User> searchUsers(Pageable pageable, User user, String searchText) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		searchText = "%" + searchText + "%";
		users = usersRepository.searchUsers(pageable,user,searchText);
		return users;
	}

	/**
	 * Metodo que devuelve un usuario a través de su id
	 * @param id
	 * @return
	 */
	public User getUser(Long id) {
		return usersRepository.findById(id).get();
	}

	/**
	 * Metodo que encripta la contraseña de un usuario y lo añade a la bbdd 
	 * @param user
	 */
	public void addUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		usersRepository.save(user);
	}

	/**
	 * Metodo que devuelve y busca un usuario a partir de su email
	 * @param email
	 * @return
	 */
	public User getUserByEmail(String email) {
		return usersRepository.findByEmail(email);
	}

	/**
	 * Metodo que borra un usuario por su id
	 * @param id
	 */
	public void deleteUser(Long id) {
		usersRepository.deleteById(id);
	}
}
