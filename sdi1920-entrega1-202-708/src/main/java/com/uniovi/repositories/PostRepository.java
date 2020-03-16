package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Post;
import com.uniovi.entities.User;

public interface PostRepository extends CrudRepository<Post, Long> {

	/**
	 * Metodo que devuelve los post de un usuario
	 * @param pageable
	 * @param user
	 * @return
	 */
	@Query("SELECT p FROM Post p WHERE p.user = ?1")
	Page<Post> getUserPosts(Pageable pageable, User user);

}
