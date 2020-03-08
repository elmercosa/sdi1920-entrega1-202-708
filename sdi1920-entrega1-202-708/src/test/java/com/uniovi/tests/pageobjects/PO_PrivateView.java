package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.uniovi.tests.utils.SeleniumUtils;

public class PO_PrivateView extends PO_NavView {

	static public void fillFormAddMark(WebDriver driver, int userOrder, String descriptionp, String scorep) {
		// Esperamos 5 segundo a que carge el DOM porque en algunos equipos falla
		SeleniumUtils.esperarSegundos(driver, 5);
		// Seleccionamos el alumnos userOrder
		new Select(driver.findElement(By.id("user"))).selectByIndex(userOrder);
		// Rellenemos el campo de descripción
		WebElement description = driver.findElement(By.name("description"));
		description.clear();
		description.sendKeys(descriptionp);
		WebElement score = driver.findElement(By.name("score"));
		score.click();
		score.clear();
		score.sendKeys(scorep);
		By boton = By.className("btn");
		driver.findElement(boton).click();
	}

	static public void checkLogin(WebDriver driver, String login, String clase, String button, String user,
			String password, String text, String message) {
		PO_HomeView.clickOption(driver, login, clase, button);
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, user, password);
		// COmprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, text, message);
	}

	static public void checkLogout(WebDriver driver, String logout, String text, String message) {
		PO_PrivateView.clickOption(driver, logout, text, message);
	}

	static public void fillFormAddTeacher(WebDriver driver, String dnit, String nombret, String apellidost, String categoriat) {
		// Rellenemos formulario
		WebElement dni = driver.findElement(By.name("dni"));
		dni.clear();
		dni.sendKeys(dnit);
		WebElement nombre = driver.findElement(By.name("nombre"));
		nombre.clear();
		nombre.sendKeys(nombret);
		WebElement apellidos = driver.findElement(By.name("apellidos"));
		apellidos.clear();
		apellidos.sendKeys(apellidost);
		WebElement categoria = driver.findElement(By.name("categoria"));
		categoria.clear();
		categoria.sendKeys(categoriat);

		// Enviamos el formulario
		By boton = By.className("btn");
		driver.findElement(boton).click();
	}

}