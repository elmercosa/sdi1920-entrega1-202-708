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
import com.uniovi.services.FriendService;
import com.uniovi.services.UsersService;

@Controller
public class FriendController {
	@Autowired
	private UsersService usersService;

	@Autowired
	private FriendService friendService;
	
	@Autowired
	private FriendRequestService friendRequestService ;

	/**
	 * Metodo encargado de responder a la peticion para aceptar a un usuario de la lista de peticiones de amistad, a continuacion, borra la peticion de amistad de la bbdd
	 * @param model
	 * @param principal usuario en sesion
	 * @param email email del usuario que ha enviardo la peticion
	 * @return
	 */
	@RequestMapping("/friend/acept/{email}")
	public String getListado(Model model, Principal principal, @PathVariable String email) {
		String fromEmail = principal.getName();
		User from = usersService.getUserByEmail(fromEmail);
		User to = usersService.getUserByEmail(email);
		friendService.addFriend(from, to);
		friendRequestService.deleteFriendRequest(from, to);
		return "redirect:/friend/request";
	}

	/**
	 * Metodo encargado responder a la peticion encargada de mostrar la lista de amigos del usuario en sesion
	 * @param model
	 * @param pageable
	 * @param principal
	 * @return
	 */
	@RequestMapping("/friend/list")
	public String getRequests(Model model, Pageable pageable , Principal principal) {
		String fromEmail = principal.getName();
		User user = usersService.getUserByEmail(fromEmail);
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		users = friendService.findAllForUser(pageable, user);
		model.addAttribute("usersList", users.getContent());
		model.addAttribute("page", users);
		return "friends/lista";
	}
}