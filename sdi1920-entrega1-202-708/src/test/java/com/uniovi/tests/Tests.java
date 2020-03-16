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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;

import com.uniovi.entities.User;
import com.uniovi.services.RolesService;
import com.uniovi.services.UsersService;

//Ordenamos las pruebas por el nombre del método
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class Tests {

	@Autowired
	private static UsersService usersService = new UsersService();

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
		driver.get(URL);
		bbdd();
	}

	// Al finalizar la última prueba
	@AfterClass
	static public void end() {
		// Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}

	/**
	 * [Prueba1] Registro de Usuario con datos válidos.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test01() throws Exception {
		TestsUtils.clickOption(driver, "signup", "class", "btn btn-primary");

		TestsUtils.fillFormRegister(driver, "prueba1@prueba.com", "Prueba1", "Prueba1", "123456", "123456");

		SeleniumUtils.EsperaCargaPagina(driver, "text", "Esto es una zona privada la web", 2);

		TestsUtils.logout(driver);
	}

	/**
	 * [Prueba2] Registro de Usuario con datos inválidos (email vacío, nombre vacío,
	 * apellidos vacíos).
	 * 
	 * @throws Exception
	 */
	@Test
	public void test02() throws Exception {
		TestsUtils.clickOption(driver, "signup", "class", "btn btn-primary");

		TestsUtils.fillFormRegister(driver, "", "Prueba2", "Prueba2", "123456", "123456");

		SeleniumUtils.EsperaCargaPagina(driver, "text", "Regístrate como usuario", 2);

		TestsUtils.fillFormRegister(driver, "prueba2@prueba.com", "", "Prueba2", "123456", "123456");

		SeleniumUtils.EsperaCargaPagina(driver, "text", "Regístrate como usuario", 2);

		TestsUtils.fillFormRegister(driver, "prueba2@prueba.com", "Prueba2", "", "123456", "123456");

		SeleniumUtils.EsperaCargaPagina(driver, "text", "Regístrate como usuario", 2);
	}

	/**
	 * [Prueba3] Registro de Usuario con datos inválidos (repetición de contraseña
	 * inválida).
	 * 
	 * @throws Exception
	 */
	@Test
	public void test03() throws Exception {
		TestsUtils.clickOption(driver, "signup", "class", "btn btn-primary");

		TestsUtils.fillFormRegister(driver, "prueba3@prueba.com", "Prueba3", "Prueba3", "123456", "123457");

		TestsUtils.checkKey(driver, "Error.signup.passwordConfirm.coincidence", Internationalization.getSPANISH());
	}

	/**
	 * [Prueba4] Registro de Usuario con datos inválidos (email existente).
	 * 
	 * @throws Exception
	 */
	@Test
	public void test04() throws Exception {
		TestsUtils.clickOption(driver, "signup", "class", "btn btn-primary");

		TestsUtils.fillFormRegister(driver, "prueba1@prueba.com", "Prueba1", "Prueba1", "123456", "123456");

		TestsUtils.checkKey(driver, "Error.signup.email.duplicate", Internationalization.getSPANISH());
	}

	/**
	 * [Prueba5] Inicio de sesión con datos válidos (administrador).
	 * 
	 * @throws Exception
	 */
	@Test
	public void test05() throws Exception {
		TestsUtils.clickOption(driver, "login", "class", "btn btn-primary");

		TestsUtils.fillFormLogin(driver, "admin@email.com", "admin");

		SeleniumUtils.EsperaCargaPagina(driver, "text", "Inicio de sesión como admin", 2);

		TestsUtils.logout(driver);
	}

	/**
	 * [Prueba6] Inicio de sesión con datos válidos (usuario estándar).
	 * 
	 * @throws Exception
	 */
	@Test
	public void test06() throws Exception {
		TestsUtils.clickOption(driver, "login", "class", "btn btn-primary");

		TestsUtils.fillFormLogin(driver, "user1@email.com", "user1");

		SeleniumUtils.EsperaCargaPagina(driver, "text", "Inicio de sesión como user", 2);

		TestsUtils.logout(driver);
	}

	/**
	 * [Prueba7] Inicio de sesión con datos inválidos (usuario estándar, campo email
	 * y contraseña vacíos).
	 * 
	 * @throws Exception
	 */
	@Test
	public void test07() throws Exception {
		TestsUtils.clickOption(driver, "login", "class", "btn btn-primary");

		TestsUtils.fillFormLogin(driver, "", "user");

		SeleniumUtils.EsperaCargaPagina(driver, "text", "Your username and password is invalid.", 2);

		TestsUtils.fillFormLogin(driver, "user1@email.com", "");

		SeleniumUtils.EsperaCargaPagina(driver, "text", "Your username and password is invalid.", 2);
	}

	/**
	 * [Prueba8] Inicio de sesión con datos válidos (usuario estándar, email
	 * existente, pero contraseña incorrecta).
	 * 
	 * @throws Exception
	 */
	@Test
	public void test08() throws Exception {
		TestsUtils.clickOption(driver, "login", "class", "btn btn-primary");

		TestsUtils.fillFormLogin(driver, "user1@email.com", "wrongPassword");

		SeleniumUtils.EsperaCargaPagina(driver, "text", "Your username and password is invalid.", 2);
	}

	/**
	 * [Prueba9] Hacer click en la opción de salir de sesión y comprobar que se
	 * redirige a la página de inicio de sesión (Login).
	 * 
	 * @throws Exception
	 */
	@Test
	public void test09() throws Exception {
		TestsUtils.clickOption(driver, "login", "class", "btn btn-primary");

		TestsUtils.fillFormLogin(driver, "admin@email.com", "admin");

		SeleniumUtils.EsperaCargaPagina(driver, "text", "Inicio de sesión como admin", 2);

		TestsUtils.logout(driver);
	}

	/**
	 * [Prueba10] Comprobar que el botón cerrar sesión no está visible si el usuario
	 * no está autenticado.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test10() throws Exception {
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Desconectar", 2);
	}

	/**
	 * [Prueba11] Mostrar el listado de usuarios y comprobar que se muestran todos
	 * los que existen en el sistema.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test11() throws Exception {
		TestsUtils.clickOption(driver, "login", "class", "btn btn-primary");

		TestsUtils.fillFormLogin(driver, "user1@email.com", "user1");

		List<WebElement> elementos = TestsUtils.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();

		elementos = TestsUtils.checkElement(driver, "free", "//a[contains(@href,'user/list')]");
		elementos.get(0).click();

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", 2);
		assertTrue(elementos.size() == 4);

		TestsUtils.logout(driver);
	}

	/**
	 * [Prueba12] Hacer una búsqueda con el campo vacío y comprobar que se muestra
	 * la página que corresponde con el listado usuarios existentes en el sistema.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test12() throws Exception {
		TestsUtils.clickOption(driver, "login", "class", "btn btn-primary");

		TestsUtils.fillFormLogin(driver, "user1@email.com", "user1");

		TestsUtils.searchUsers(driver, "");

		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", 2);
		assertTrue(elementos.size() == 4);

		TestsUtils.logout(driver);
	}

	/**
	 * [Prueba13] Hacer una búsqueda escribiendo en el campo un texto que no exista
	 * y comprobar que se muestra la página que corresponde, con la lista de
	 * usuarios vacía.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test13() throws Exception {
		TestsUtils.clickOption(driver, "login", "class", "btn btn-primary");

		TestsUtils.fillFormLogin(driver, "user1@email.com", "user1");

		TestsUtils.searchUsers(driver, "wrong");

		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "wrong", 2);

		TestsUtils.logout(driver);
	}

	/**
	 * [Prueba14] Hacer una búsqueda con un texto específico y comprobar que se
	 * muestra la página que corresponde, con la lista de usuarios en los que el
	 * texto especificados sea parte de su nombre, apellidos o de su email.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test14() throws Exception {
		TestsUtils.clickOption(driver, "login", "class", "btn btn-primary");

		TestsUtils.fillFormLogin(driver, "user1@email.com", "user1");

		TestsUtils.searchUsers(driver, "user");

		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", 2);
		assertTrue(elementos.size() == 3);

		TestsUtils.logout(driver);
	}

	/**
	 * [Prueba15] Desde el listado de usuarios de la aplicación, enviar una
	 * invitación de amistad a un usuario. Comprobar que la solicitud de amistad
	 * aparece en el listado de invitaciones (punto siguiente).
	 * 
	 * @throws Exception
	 */
	@Test
	public void test15() throws Exception {
		TestsUtils.clickOption(driver, "login", "class", "btn btn-primary");

		TestsUtils.fillFormLogin(driver, "user1@email.com", "user1");

		TestsUtils.searchUsers(driver, "user2@email.com");

		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", 2);
		assertTrue(elementos.size() == 1);

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "text", "agregar amigo", 2);
		assertTrue(elementos.size() == 1);

		elementos.get(0).click();

		TestsUtils.logout(driver);

		TestsUtils.clickOption(driver, "login", "class", "btn btn-primary");

		TestsUtils.fillFormLogin(driver, "user2@email.com", "user2");

		elementos = TestsUtils.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();

		elementos = TestsUtils.checkElement(driver, "free", "//a[contains(@href,'friend/request')]");
		elementos.get(0).click();

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", 2);
		assertTrue(elementos.size() == 1);

		TestsUtils.logout(driver);
	}

	/**
	 * [Prueba16] Desde el listado de usuarios de la aplicación, enviar una
	 * invitación de amistad a un usuario al que ya le habíamos enviado la
	 * invitación previamente. No debería dejarnos enviar la invitación, se podría
	 * ocultar el botón de enviar invitación o notificar que ya había sido enviada
	 * previamente.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test16() throws Exception {
		TestsUtils.clickOption(driver, "login", "class", "btn btn-primary");

		TestsUtils.fillFormLogin(driver, "user1@email.com", "user1");

		TestsUtils.searchUsers(driver, "user2@email.com");

		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", 2);
		assertTrue(elementos.size() == 1);

		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "agregar amigo", 2);

		TestsUtils.logout(driver);
	}

	/**
	 * [Prueba17] Mostrar el listado de invitaciones de amistad recibidas. Comprobar
	 * con un listado que contenga varias invitaciones recibidas.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test17() throws Exception {
		TestsUtils.clickOption(driver, "login", "class", "btn btn-primary");

		TestsUtils.fillFormLogin(driver, "user3@email.com", "user3");

		TestsUtils.searchUsers(driver, "user2@email.com");

		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", 2);
		assertTrue(elementos.size() == 1);

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "text", "agregar amigo", 2);
		assertTrue(elementos.size() == 1);

		elementos.get(0).click();

		TestsUtils.logout(driver);

		TestsUtils.clickOption(driver, "login", "class", "btn btn-primary");

		TestsUtils.fillFormLogin(driver, "user2@email.com", "user2");

		elementos = TestsUtils.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();

		elementos = TestsUtils.checkElement(driver, "free", "//a[contains(@href,'friend/request')]");
		elementos.get(0).click();

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", 2);
		assertTrue(elementos.size() == 2);

		TestsUtils.logout(driver);
	}

	/**
	 * [Prueba18] Sobre el listado de invitaciones recibidas. Hacer click en el
	 * botón/enlace de una de ellas y comprobar que dicha solicitud desaparece del
	 * listado de invitaciones.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test18() throws Exception {
		TestsUtils.clickOption(driver, "login", "class", "btn btn-primary");

		TestsUtils.fillFormLogin(driver, "user2@email.com", "user2");

		List<WebElement> elementos = TestsUtils.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();

		elementos = TestsUtils.checkElement(driver, "free", "//a[contains(@href,'friend/request')]");
		elementos.get(0).click();

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", 2);
		assertTrue(elementos.size() == 2);

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "@href", "friend/acept", 2);
		assertTrue(elementos.size() == 2);
		elementos.get(0).click();

		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "User1", 2);

		TestsUtils.logout(driver);
	}

	/**
	 * [Prueba19] Mostrar el listado de amigos de un usuario. Comprobar que el
	 * listado contiene los amigos que deben ser.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test19() throws Exception {
		TestsUtils.clickOption(driver, "login", "class", "btn btn-primary");

		TestsUtils.fillFormLogin(driver, "user2@email.com", "user2");

		List<WebElement> elementos = TestsUtils.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();

		elementos = TestsUtils.checkElement(driver, "free", "//a[contains(@href,'friend/list')]");
		elementos.get(0).click();

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", 2);
		assertTrue(elementos.size() == 1);

		TestsUtils.checkElement(driver, "text", "User1");

		TestsUtils.logout(driver);
	}

	/**
	 * [Prueba20] Visualizar al menos cuatro páginas en Español/Inglés/Español
	 * (comprobando que algunas de las etiquetas cambian al idioma correspondiente).
	 * Ejemplo, Página principal/Opciones Principales de Usuario/Listado de
	 * Usuarios.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test20() throws Exception {
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				TestsUtils.p.getString("welcome.message", Internationalization.getSPANISH()), 2);
		TestsUtils.changeLanguage(driver, "btnEnglish");
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				TestsUtils.p.getString("welcome.message", Internationalization.getENGLISH()), 2);
		TestsUtils.changeLanguage(driver, "btnSpanish");
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				TestsUtils.p.getString("welcome.message", Internationalization.getSPANISH()), 2);

		TestsUtils.clickOption(driver, "signup", "class", "btn btn-primary");

		SeleniumUtils.EsperaCargaPagina(driver, "text",
				TestsUtils.p.getString("signup.message", Internationalization.getSPANISH()), 2);
		TestsUtils.changeLanguage(driver, "btnEnglish");
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				TestsUtils.p.getString("signup.message", Internationalization.getENGLISH()), 2);
		TestsUtils.changeLanguage(driver, "btnSpanish");
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				TestsUtils.p.getString("signup.message", Internationalization.getSPANISH()), 2);

		TestsUtils.clickOption(driver, "login", "class", "btn btn-primary");

		SeleniumUtils.EsperaCargaPagina(driver, "text",
				TestsUtils.p.getString("login.message", Internationalization.getSPANISH()), 2);
		TestsUtils.changeLanguage(driver, "btnEnglish");
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				TestsUtils.p.getString("login.message", Internationalization.getENGLISH()), 2);
		TestsUtils.changeLanguage(driver, "btnSpanish");
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				TestsUtils.p.getString("login.message", Internationalization.getSPANISH()), 2);

		TestsUtils.fillFormLogin(driver, "user1@email.com", "user1");

		SeleniumUtils.EsperaCargaPagina(driver, "text",
				TestsUtils.p.getString("zona.privada.message", Internationalization.getSPANISH()), 2);
		TestsUtils.changeLanguage(driver, "btnEnglish");
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				TestsUtils.p.getString("zona.privada.message", Internationalization.getENGLISH()), 2);
		TestsUtils.changeLanguage(driver, "btnSpanish");
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				TestsUtils.p.getString("zona.privada.message", Internationalization.getSPANISH()), 2);

		TestsUtils.logout(driver);
	}

	/**
	 * [Prueba21] Intentar acceder sin estar autenticado a la opción de listado de
	 * usuarios. Se deberá volver al formulario de login.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test21() throws Exception {
		driver.get("http://localhost:8090/user/list");

		SeleniumUtils.EsperaCargaPagina(driver, "text", "Nombre de usuario", 2);
	}

	/**
	 * [Prueba22] Intentar acceder sin estar autenticado a la opción de listado de
	 * publicaciones de un usuario estándar. Se deberá volver al formulario de
	 * login.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test22() throws Exception {
		driver.get("http://localhost:8090/friend/list");

		SeleniumUtils.EsperaCargaPagina(driver, "text", "Nombre de usuario", 2);
	}

	/**
	 * [Prueba23] Estando autenticado como usuario estándar intentar acceder a una
	 * opción disponible solo para usuarios administradores (Se puede añadir una
	 * opción cualquiera en el menú). Se deberá indicar un mensaje de acción
	 * prohibida.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test23() throws Exception {
		TestsUtils.clickOption(driver, "login", "class", "btn btn-primary");

		TestsUtils.fillFormLogin(driver, "user1@email.com", "user1");

		driver.get("http://localhost:8090/admin");

		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Solo puedes ver esto si eres admin", 2);
	}

	/**
	 * [Prueba24] Ir al formulario crear publicaciones, rellenarla con datos válidos
	 * y pulsar el botón Submit. Comprobar que la publicación sale en el listado de
	 * publicaciones de dicho usuario.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test24() throws Exception {
		TestsUtils.clickOption(driver, "login", "class", "btn btn-primary");
		
		TestsUtils.fillFormLogin(driver, "user1@email.com", "user1");
		
		List<WebElement> elementos = TestsUtils.checkElement(driver, "free", "//li[contains(@id, 'post-menu')]/a");
		elementos.get(0).click();

		elementos = TestsUtils.checkElement(driver, "free", "//a[contains(@href,'post/add')]");
		elementos.get(0).click();
		
		TestsUtils.fillFormPost(driver, "Prueba", "Nota de prueba");
		
		elementos = TestsUtils.checkElement(driver, "free", "//li[contains(@id, 'post-menu')]/a");
		elementos.get(0).click();

		elementos = TestsUtils.checkElement(driver, "free", "//a[contains(@href,'post/list')]");
		elementos.get(0).click();
		
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Prueba", 2);
		
		TestsUtils.logout(driver);
	}

	/**
	 * [Prueba25] Ir al formulario de crear publicaciones, rellenarla con datos
	 * inválidos (campo título vacío) y pulsar el botón Submit. Comprobar que se
	 * muestra el mensaje de campo obligatorio.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test25() throws Exception {
TestsUtils.clickOption(driver, "login", "class", "btn btn-primary");
		
		TestsUtils.fillFormLogin(driver, "user1@email.com", "user1");
		
		List<WebElement> elementos = TestsUtils.checkElement(driver, "free", "//li[contains(@id, 'post-menu')]/a");
		elementos.get(0).click();

		elementos = TestsUtils.checkElement(driver, "free", "//a[contains(@href,'post/add')]");
		elementos.get(0).click();
		
		TestsUtils.fillFormPost(driver, "", "Nota sin título");
		
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Este campo no puede ser vacío", 2);
		
		TestsUtils.fillFormPost(driver, "Nota sin descripción", "");
		
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Este campo no puede ser vacío", 2);
		
		TestsUtils.logout(driver);
	}

	/**
	 * Borrar BBDD
	 * 
	 * @throws Exception
	 */
	public static void bbdd() {
		TestsUtils.clickOption(driver, "login", "class", "btn btn-primary");

		TestsUtils.fillFormLogin(driver, "admin@email.com", "admin");

		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "@href", "bbdd/reset", 2);
		assertTrue(elementos.size() == 1);
		elementos.get(0).click();

		TestsUtils.logout(driver);
	}

}
