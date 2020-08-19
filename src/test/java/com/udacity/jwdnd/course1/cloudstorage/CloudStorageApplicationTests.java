package com.udacity.jwdnd.course1.cloudstorage;

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

	@Test
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

	void driveTo(String url) {
		driver.get("http://localhost:" + port + url);
	}

}
