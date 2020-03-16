package com.uniovi.controllers;

import java.security.Principal;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.Post;
import com.uniovi.entities.User;
import com.uniovi.services.FriendService;
import com.uniovi.services.PostService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.PostFormValidator;

@Controller
public class PostController {
	@Autowired // Inyectar el servicio
	private PostService postService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private PostFormValidator postFormValidator;
	@Autowired
	private FriendService friendService ;
	

	@RequestMapping("/post/list")
	public String getList(Model model, Principal principal, Pageable pageable) {
		Page<Post> posts = new PageImpl<Post>(new LinkedList<Post>());
		String userEmail = principal.getName();
		User user = usersService.getUserByEmail(userEmail);
		posts = postService.getUserPosts(pageable, user);
		model.addAttribute("postList", posts.getContent());
		model.addAttribute("page", posts);
		return "post/list";
	}

	@RequestMapping(value = "/post/add", method = RequestMethod.POST)
	public String setPost(@ModelAttribute Post post, BindingResult result, Model model, Principal principal) {
		postFormValidator.validate(post, result);
		String userEmail = principal.getName();
		User user = usersService.getUserByEmail(userEmail);
		if (result.hasErrors()) {
			return "post/add";
		}
		post.setUser(user);
		java.util.Date actual = new java.util.Date();
		post.setDate(new java.sql.Date(actual.getTime()));
		postService.addPost(post);
		return "redirect:/post/list";
	}
	
	@RequestMapping(value = "/post/add")
	public String getPost(Model model) {
		model.addAttribute("post", new Post());
		return "post/add";
	}
	
	@RequestMapping("/post/list/friend/{email}")
	public String getListForFriend(Model model, Principal principal, Pageable pageable, @PathVariable String email) {
		Page<Post> posts = new PageImpl<Post>(new LinkedList<Post>());
		User user = usersService.getUserByEmail(email);
		User actual = usersService.getUserByEmail(principal.getName());
		if(friendService.findFriendship(actual, user)) {
			posts = postService.getUserPosts(pageable, user);
			model.addAttribute("postList", posts.getContent());
			model.addAttribute("page", posts);
			return "post/friend";
		}else {
			return "/home";
		}
	}
}
