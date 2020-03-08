package com.uniovi.tests;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_PrivateView;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.utils.SeleniumUtils;

//Ordenamos las pruebas por el nombre del método
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class Tests {

	// En Windows (Debe ser la versión 65.0.1 y desactivar las actualizacioens
	// automáticas)):
	static String PathFirefox65 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	static String Geckdriver024 = "D:\\Jaime\\Repositorios\\sdi1920-entrega1-202-708\\sdi1920-entrega1-202-708\\geckodriver024win64.exe";

	// Común a Windows y a MACOSX
	static WebDriver driver = getDriver(PathFirefox65, Geckdriver024);
	static String URL = "http://localhost:8090";

	public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", Geckdriver);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	// Antes de cada prueba se navega al URL home de la aplicaciónn
	@Before
	public void setUp() {
		driver.navigate().to(URL);
	}

	// Después de cada prueba se borran las cookies del navegador
	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	// Antes de la primera prueba
	@BeforeClass
	static public void begin() {
	}

	// Al finalizar la última prueba
	@AfterClass
	static public void end() {
		// Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}

	// Ejercicio 1

	// [Prueba1] Registro de Usuario con datos válidos.
	@Test
	public void test01() throws Exception {
		driver.get("http://localhost:8090/");
 
		TestsUtils.clickOption(driver, "signup", "class", "btn btn-primary");
		
		TestsUtils.fillFormRegister(driver, "prueba1@prueba.com", "Prueba1", "Prueba1", "123456", "123456");

		SeleniumUtils.EsperaCargaPagina(driver, "text", "Esto es una zona privada la web", 2);
		
		TestsUtils.clickOption(driver, "logout", "text", "Identifícate");

	}

	// [Prueba2] Registro de Usuario con datos inválidos (email vacío, nombre vacío,
	// apellidos vacíos).
	@Test
	public void test02() throws Exception {
		driver.get("http://localhost:8090/");

		TestsUtils.clickOption(driver, "signup", "class", "btn btn-primary");
		
		TestsUtils.fillFormRegister(driver, "", "Prueba2", "Prueba2", "123456", "123456");

//		TestsUtils.checkKey(driver, "Error.empty", TestsUtils.p.getSPANISH());
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Registráte como usuario", 2);
		
		TestsUtils.fillFormRegister(driver, "prueba2@prueba.com", "", "Prueba2", "123456", "123456");
		
//		TestsUtils.checkKey(driver, "Error.empty", TestsUtils.p.getSPANISH());
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Registráte como usuario", 2);
		
		TestsUtils.fillFormRegister(driver, "prueba2@prueba.com", "Prueba2", "", "123456", "123456");
		
//		TestsUtils.checkKey(driver, "Error.empty", TestsUtils.p.getSPANISH());
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Registráte como usuario", 2);

	}

	// [Prueba3] Registro de Usuario con datos inválidos (repetición de contraseña
	// inválida).
	@Test
	public void test03() throws Exception {
		driver.get("http://localhost:8090/");
		
		TestsUtils.clickOption(driver, "signup", "class", "btn btn-primary");

		TestsUtils.fillFormRegister(driver, "prueba3@prueba.com", "Prueba3", "Prueba3", "123456", "123457");

		TestsUtils.checkKey(driver, "Error.signup.passwordConfirm.coincidence", TestsUtils.p.getSPANISH());
	}

	// [Prueba4] Registro de Usuario con datos inválidos (email existente).
	@Test
	public void test04() throws Exception {
		driver.get("http://localhost:8090/");
		
		TestsUtils.clickOption(driver, "signup", "class", "btn btn-primary");

		TestsUtils.fillFormRegister(driver, "prueba1@prueba.com", "Prueba1", "Prueba1", "123456", "123456");

		TestsUtils.checkKey(driver, "Error.signup.email.duplicate", TestsUtils.p.getSPANISH());
	}
}
