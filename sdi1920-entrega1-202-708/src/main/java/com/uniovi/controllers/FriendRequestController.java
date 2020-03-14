package com.uniovi.controllers;

import java.security.Principal;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uniovi.entities.User;
import com.uniovi.services.FriendRequestService;
import com.uniovi.services.UsersService;

@Controller
public class FriendRequestController {
	@Autowired
	private UsersService usersService;

	@Autowired
	private FriendRequestService friendRequestService;

	/**
	 * Metodo encargado de responder a la peticion para enviar la peticion de amistad a un usuario de la lista
	 * @param model
	 * @param principal
	 * @param email
	 * @return
	 */
	@RequestMapping("/friend/add/{email}")
	public String getListado(Model model, Principal principal, @PathVariable String email) {
		String fromEmail = principal.getName();
		User from = usersService.getUserByEmail(fromEmail);
		User to = usersService.getUserByEmail(email);
		if (friendRequestService.searchFriendRequest(from, to) == 0) {
			friendRequestService.sendFriendRequest(from, to);
		}
		return "redirect:/user/list";
	}

	/**
	 * Metodo encargado de responder a la peticion para ver la lista de peticiones de amistad que tiene el usuario en sesion
	 * @param model
	 * @param pageable
	 * @param principal
	 * @return
	 */
	@RequestMapping("/friend/request")
	public String getRequests(Model model, Pageable pageable , Principal principal) {
		String fromEmail = principal.getName();
		User user = usersService.getUserByEmail(fromEmail);
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		users = friendRequestService.findAllForUser(pageable, user);
		model.addAttribute("usersList", users.getContent());
		model.addAttribute("page", users);
		return "friendRequest/lista";
	}
}