package com.uniovi.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.FriendRequests;

public interface FriendRequestRepository extends CrudRepository<FriendRequests, Long> {
	
	@Query("SELECT COUNT(*) FROM FriendRequests fr WHERE fr.friendId = ?2 AND fr.personId = ?1")
	int findFriendRequest(Long from, Long to);

}
