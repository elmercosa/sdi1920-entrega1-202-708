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
		posts = postService.getUserPosts(pageable, user);
		model.addAttribute("postList", posts.getContent());
		model.addAttribute("page", posts);
		return "post/friend";
	}

//	@RequestMapping("/mark/details/{id}")
//	public String getDetail(Model model, @PathVariable Long id) {
//		model.addAttribute("mark", marksService.getMark(id));
//		return "mark/details";
//	}
//
//	@RequestMapping("/mark/delete/{id}")
//	public String deleteMark(@PathVariable Long id) {
//		marksService.deleteMark(id);
//		return "redirect:/mark/list";
//	}
//
//	@RequestMapping(value = "/mark/add")
//	public String getMark(Model model) {
//		model.addAttribute("mark", new Mark());
//		model.addAttribute("usersList", usersService.getUsers());
//		return "mark/add";
//	}
//
//	@RequestMapping(value = "/mark/edit/{id}")
//	public String getEdit(Model model, @PathVariable Long id) {
//		model.addAttribute("usersList", usersService.getUsers());
//		return "mark/edit";
//	}
//
//	@RequestMapping(value = "/mark/edit/{id}", method = RequestMethod.POST)
//	public String setEdit(Model model, @PathVariable Long id, @ModelAttribute Mark mark) {
//		Mark original = marksService.getMark(id);
//		// modificar solo score y description
//		original.setScore(mark.getScore());
//		original.setDescription(mark.getDescription());
//		marksService.addMark(original);
//		return "redirect:/mark/details/" + id;
//	}
//
//	@RequestMapping("/mark/list/update")
//	public String updateList(Model model, Pageable pageable ,Principal principal) {
//		String dni = principal.getName(); // DNI es el name de la autenticación
//		User user = usersService.getUserByDni(dni);
//		Page<Mark> marks = marksService.getMarksForUser(pageable, user);
//		model.addAttribute("markList", marks.getContent());
//		return "mark/list :: tableMarks";
//	}
//
//	@RequestMapping(value = "/mark/{id}/resend", method = RequestMethod.GET)
//	public String setResendTrue(Model model, @PathVariable Long id) {
//		marksService.setMarkResend(true, id);
//		return "redirect:/mark/list";
//	}
//
//	@RequestMapping(value = "/mark/{id}/noresend", method = RequestMethod.GET)
//	public String setResendFalse(Model model, @PathVariable Long id) {
//		marksService.setMarkResend(false, id);
//		return "redirect:/mark/list";
//	}

}
