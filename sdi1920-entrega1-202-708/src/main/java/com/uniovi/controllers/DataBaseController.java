package com.uniovi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uniovi.services.DataBaseService;

@Controller
public class DataBaseController {
	@Autowired
	private DataBaseService dataBaseService;

	@RequestMapping("/bbdd/reset")
	public String getListado(Model model) {
		dataBaseService.resetDataBase();
		return "redirect:/home";
	}
}