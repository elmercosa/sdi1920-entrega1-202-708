package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.User;

public interface UsersRepository extends CrudRepository<User, Long> {

	/**
	 * Metodo que busca un usuario por su email
	 * 
	 * @param email
	 * @return
	 */
	User findByEmail(String email);

	/**
	 * Metodo que devuelve la lista de usuarios del sistema que no sean ni el
	 * usuario actual ni los admin con paginacion
	 * 
	 * @param pageable
	 * @param user
	 * @return
	 */
	@Query("SELECT u FROM User u WHERE u != ?1 AND u.role != 'ROLE_ADMIN'")
	Page<User> listUsers(Pageable pageable, User user);
	
	/**
	 * Metodo que devuelve la lista de usuarios del sistema que no sean ni el
	 * usuario actual ni los admin con paginacion, version ADMIN
	 * 
	 * @param pageable
	 * @param user
	 * @return
	 */
	@Query("SELECT u FROM User u")
	Page<User> listUsersAdmin(Pageable pageable);

	/**
	 * Metodo que devuelve la lista de usuarios del sistema que no sean ni el
	 * usuario actual ni los admin con paginacion y que filtra a los usuarios que
	 * contengan la cadena de texto proporcionada en su nombre, email o apellido
	 * 
	 * @param pageable
	 * @param user
	 * @param searchText
	 * @return
	 */
	@Query("SELECT u FROM User u WHERE u != ?1 AND u.role != 'ROLE_ADMIN' AND ( LOWER(u.name) LIKE LOWER(?2) OR LOWER(u.lastName) LIKE LOWER(?2) OR LOWER(u.email) LIKE LOWER(?2) )")
	Page<User> searchUsers(Pageable pageable, User user, String searchText);
}
