package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.FriendRequests;

public interface FriendRequestRepository extends CrudRepository<FriendRequests, Long> {
	
	/**
	 * Metodo que busca si existe peticion de amistad entre dos usuarios
	 * @param from
	 * @param to
	 * @return
	 */
	@Query("SELECT COUNT(*) FROM FriendRequests fr WHERE fr.friendId = ?2 AND fr.personId = ?1")
	int findFriendRequest(Long from, Long to);

	/**
	 * Metodo que busca todas las peticiones de amistad de un usuario con paginacion
	 * @param pageable
	 * @param user
	 * @return
	 */
	@Query("SELECT fr.personId FROM FriendRequests fr WHERE fr.friendId = ?1")
	Page<Long> findAllForUser(Pageable pageable, Long user);
	
	/**
	 * Metodo que busca todas las peticiones de amistad de un usuario para filtrar la lista de usuarios
	 * @param user1
	 * @param user2
	 * @return
	 */
	@Query("SELECT fr FROM FriendRequests fr WHERE (fr.friendId = ?2 AND fr.personId = ?1) OR (fr.friendId = ?1 AND fr.personId = ?2)")
	List<FriendRequests> findFriendRequestFor(Long user1, Long user2);
}
