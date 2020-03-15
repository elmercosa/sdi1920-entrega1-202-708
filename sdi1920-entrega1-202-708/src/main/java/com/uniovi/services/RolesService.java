package com.uniovi.services;

import org.springframework.stereotype.Service;

@Service
public class RolesService {
	String[] roles = { "ROLE_USER", "ROLE_ADMIN" };

	/**
	 * Metodo que devuelve los roles de usuario que existen en el sistema
	 * @return
	 */
	public String[] getRoles() {
		return roles;
	}
}
