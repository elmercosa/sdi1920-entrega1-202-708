package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.FriendRequests;

public interface FriendRequestRepository extends CrudRepository<FriendRequests, Long> {
	
	@Query("SELECT COUNT(*) FROM FriendRequests fr WHERE fr.friendId = ?2 AND fr.personId = ?1")
	int findFriendRequest(Long from, Long to);

	@Query("SELECT fr.personId FROM FriendRequests fr WHERE fr.friendId = ?1")
	Page<Long> findAllForUser(Pageable pageable, Long user);
	
	@Query("SELECT fr FROM FriendRequests fr WHERE (fr.friendId = ?2 AND fr.personId = ?1) OR (fr.friendId = ?1 AND fr.personId = ?2)")
	FriendRequests findFriendRequestFor(Long user1, Long user2);
}
