package com.uniovi.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
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
}