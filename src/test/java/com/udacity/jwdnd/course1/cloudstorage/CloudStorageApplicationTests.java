package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.ui.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.ui.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.ui.RegistrationPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private static WebDriver driver;
	private static final String USER_NAME = "oenriquez";
	private static final String PASSWORD = "password";

	@BeforeAll
	public static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	@AfterAll
	public static void afterAll() {
		driver.quit();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	public void getLoginPage() {
		driveTo("/signup");
		RegistrationPage signupPage = new RegistrationPage(driver);
		signupPage.signup("Oscar", "Enriquez", USER_NAME, PASSWORD);

		driveTo("/login");
		WebDriverWait wait = new WebDriverWait(driver, 1000);
		wait.until(webDriver -> webDriver.findElement(By.id("inputUsername")));
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(USER_NAME, PASSWORD);
	}

	@Test
	public void testSignUpAndLoginFlow() throws InterruptedException {
		driveTo("/home");
		Thread.sleep(1000);
		LoginPage loginPage = new LoginPage(driver);

		assertEquals("inputUsername", loginPage.getUserNameInputId());

		this.getLoginPage();
		Thread.sleep(1000);

		HomePage homePage = new HomePage(driver);

		assertEquals(HomePage.LOGOUT_BUTTON_TEXT, homePage.getLogoutButtonText());
		homePage.logout();
		WebDriverWait wait = new WebDriverWait(driver, 1000);
		wait.until(webDriver -> webDriver.findElement(By.id("inputUsername")));

		driveTo("/home");
		Thread.sleep(1000);
		assertEquals("inputUsername", loginPage.getUserNameInputId());
	}

	@Test
	public void testNotesCreateUpdateDelete() throws InterruptedException {
		this.getLoginPage();
		Thread.sleep(1000);

		HomePage homePage = new HomePage(driver);
		Thread.sleep(1000);

		//Create Notes
		homePage.navigateToNotes();
		Thread.sleep(1000);

		homePage.showNoteModal();
		Thread.sleep(1000);

		homePage.addNote("Note 1", "First Description");
		Thread.sleep(1000);

		driveTo("/home");
		Thread.sleep(1000);

		homePage.navigateToNotes();
		Thread.sleep(2000);

		Note note = homePage.getLastNoteAdded();

		assertEquals("Note 1", note.getNoteTitle());
		assertEquals("First Description", note.getNoteDescription());

		//Update Notes
		homePage.navigateToNotes();
		Thread.sleep(1000);

		homePage.showNoteModalUpdate();
		Thread.sleep(2000);

		homePage.updateNote(note.getNoteId(), "Note 2", "Second Description");
		Thread.sleep(1000);

		driveTo("/home");
		Thread.sleep(1000);

		homePage.navigateToNotes();
		Thread.sleep(1000);

		Note noteUpdate = homePage.getLastNoteAdded();
		assertEquals("Note 2", noteUpdate.getNoteTitle());
		assertEquals("Second Description", noteUpdate.getNoteDescription());

		//Delete Notes
		homePage.deleteLastNoteAdded();

		driveTo("/home");
		Thread.sleep(1000);

		Thread.sleep(1000);
		homePage.navigateToNotes();

		Note noteDeleted = homePage.getLastNoteAdded();
		assertNull(noteDeleted);

		homePage.logout();
	}

	@Test
	public void testCredentialsCreateUpdateDelete() throws InterruptedException {
		this.getLoginPage();
		Thread.sleep(1000);

		HomePage homePage = new HomePage(driver);
		Thread.sleep(1000);

		//Create Credentials
		homePage.navigateToCredentials();
		Thread.sleep(1000);

		homePage.showCredentialModal();
		Thread.sleep(1000);

		homePage.addCredential("Credential 1", "First UserName", "First Password");
		Thread.sleep(1000);

		driveTo("/home");
		Thread.sleep(1000);

		homePage.navigateToCredentials();
		Thread.sleep(1000);

		Credential credential = homePage.getLastCredentialAdded();

		assertEquals("Credential 1", credential.getUrl());
		assertEquals("First UserName", credential.getUsername());
		assertEquals("First Password", credential.getPassword());

		//Update Credentials
		homePage.showCredentialModalUpdate();
		Thread.sleep(1000);

		homePage.updateCredential(credential.getCredentialId(), "Credential 2", "Second UserName", "Second Password");
		Thread.sleep(1000);

		driveTo("/home");
		Thread.sleep(1000);

		homePage.navigateToCredentials();
		Thread.sleep(1000);

		Credential credentialUpdate = homePage.getLastCredentialAdded();
		Thread.sleep(1000);

		assertEquals("Credential 2", credentialUpdate.getUrl());
		assertEquals("Second UserName", credentialUpdate.getUsername());
		assertEquals("Second Password", credentialUpdate.getPassword());

		//Delete Credentials
		homePage.deleteLastCredentialAdded();

		driveTo("/home");
		Thread.sleep(1000);

		homePage.navigateToCredentials();
		Thread.sleep(1000);

		Credential credentialAdded = homePage.getLastCredentialAdded();
		assertNull(credentialAdded);

		homePage.logout();
	}

	void driveTo(String url) {
		driver.get("http://localhost:" + port + url);
	}

}
