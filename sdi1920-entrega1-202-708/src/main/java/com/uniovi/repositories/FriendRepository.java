package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Friends;

public interface FriendRepository extends CrudRepository<Friends, Long> {
	
	@Query("SELECT COUNT(*) FROM Friends f WHERE (f.friendId = ?2 AND f.personId = ?1) OR (f.friendId = ?1 AND f.personId = ?2)")
	int findFriendship(Long from, Long to);

	@Query("SELECT fr FROM Friends fr WHERE fr.friendId = ?1 OR fr.personId = ?1")
	Page<Friends> findFriendsForUser(Pageable pageable, Long user);
	
	@Query("SELECT fr FROM Friends fr WHERE fr.friendId = ?1 OR fr.personId = ?1")
	List<Friends> findAllFriendsForUser(Long user);
	
}
