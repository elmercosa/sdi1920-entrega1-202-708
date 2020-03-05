package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.User;

public interface UsersRepository extends CrudRepository<User, Long> {

	User findByEmail(String email);
	
	@Query("SELECT u FROM User u WHERE u != ?1 AND u.role != 'ROLE_ADMIN'")
	Page<User> listUsers(Pageable pageable, User user);
	
	@Query("SELECT u FROM User u WHERE u != ?1 AND u.role != 'ROLE_ADMIN' AND ( LOWER(u.name) LIKE LOWER(?2) OR LOWER(u.lastName) LIKE LOWER(?2) OR LOWER(u.email) LIKE LOWER(?2) )")
	Page<User> searchUsers(Pageable pageable, User user, String searchText);
}
