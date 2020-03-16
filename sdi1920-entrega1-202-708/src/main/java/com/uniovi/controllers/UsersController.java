package com.uniovi.controllers;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.User;
import com.uniovi.services.FriendService;
import com.uniovi.services.RolesService;
import com.uniovi.services.SecurityService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.SignUpFormValidator;

@Controller
public class UsersController {
	@Autowired
	private UsersService usersService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private RolesService rolesService;

	@Autowired
	private SignUpFormValidator signUpFormValidator;

	@Autowired
	private FriendService friendService;

	/**
	 * Metodo encargado de responer a la peticion para ver la lista de usuarios del
	 * sistema, exceptuando los administradores y el usuario actual de sesion
	 * 
	 * @param model
	 * @param pageable
	 * @param principal
	 * @param searchText Texto de busqueda de usuarios
	 * @return
	 */
	@RequestMapping("/user/list")
	public String getListado(Model model, Pageable pageable, Principal principal,
			@RequestParam(value = "", required = false) String searchText) {
		String email = principal.getName();
		User user = usersService.getUserByEmail(email);
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		if (searchText != null && !searchText.isEmpty()) {
			users = usersService.searchUsers(pageable, user, searchText);
		} else {
			users = usersService.getUsers(pageable, user);
		}
		model.addAttribute("usersList", comprobarPeticiones(user, users.getContent()));
		model.addAttribute("page", users);
		return "user/list";
	}
	
	/**
	 * Metodo encargado de responer a la peticion para ver la lista de usuarios del
	 * sistema, exceptuando los administradores y el usuario actual de sesion
	 * 
	 * @param model
	 * @param pageable
	 * @param principal
	 * @param searchText Texto de busqueda de usuarios
	 * @return
	 */
	@RequestMapping("/user/list/admin")
	public String getListadoAdmin(Model model, Pageable pageable, Principal principal) {
		String email = principal.getName();
		User user = usersService.getUserByEmail(email);
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		users = usersService.getUsersAdmin(pageable);
		model.addAttribute("usersList", comprobarPeticiones(user, users.getContent()));
		model.addAttribute("page", users);
		return "admin/list";
	}

	/**
	 * Metodo privado encargado de filtrar la pagina actual de usuarios para no
	 * mostrar el boton de agregar amigo si ya se ha enviado la peticion o ya son
	 * amigos
	 * 
	 * @param user  usuario en sesion
	 * @param users pagina actual de usuarios
	 * @return lista filtrada
	 */
	private List<User> comprobarPeticiones(User user, List<User> users) {
		Set<User> friendRequest = user.getFriendrequest();
		for (User user2 : users) {
			if (friendRequest.contains(user2)) {
				user2.setFriendRequest(true);
			} else {
				user2.setFriendRequest(false);
			}
		}
		List<User> amigos = friendService.findAllFriendsForUser(user);
		for (User user2 : users) {
			if (amigos.contains(user2)) {
				user2.setFriendRequest(true);
			}
		}
		return users;
	}

	/**
	 * Metodo get encargado de responder a la peticion para registrar un usuario
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}

	/**
	 * Metodo post encargado de responder a la peticion del formulario de registro
	 * para guardar el nuevo usuario
	 * 
	 * @param user
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@Validated User user, BindingResult result) {
		signUpFormValidator.validate(user, result);
		if (result.hasErrors()) {
			return "signup";
		}
		user.setRole(rolesService.getRoles()[0]);
		usersService.addUser(user);
		securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
		return "redirect:home";
	}

	/**
	 * Metodo get encargado de responder a la peticion para iniciar sesion, devuelve
	 * mensajes de error si falla el login y cuando se desloguea el usuario
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, String error, String logout) {
		if (error != null)
			model.addAttribute("error", "Your username and password is invalid.");
		if (logout != null)
			model.addAttribute("message", "You have been logged out successfully.");

		return "login";
	}

	/**
	 * Metodo get encargado de redirigir al usuario que inicia sesion a la pagina
	 * home
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home(Model model) {
		return "home";
	}

	/**
	 * Metodo get encargado de redirigir al admin a su vista (Prueba 23)
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/admin")
	public String admin(Model model) {
		return "admin/admin";
	}
}