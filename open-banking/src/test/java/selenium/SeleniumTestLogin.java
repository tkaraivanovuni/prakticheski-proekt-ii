package selenium;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.function.Function;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Test class for login and registration functions.
 */
public class SeleniumTestLogin {
	
private WebDriver driver;
	
	/**
	 * This method sets the properties for the driver.
	 */
	@BeforeAll
	public static void setupClass() {
		System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
	}
	
	/**
	 * This class opens a new driver before each test.
	 */
	@BeforeEach
	public void setup() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--ignore-certificate-errors");
		options.addArguments("--incognito");
		driver = new ChromeDriver(options);
	}
	
	/**
	 * This method closes the driver after each test.
	 */
	@AfterEach
	public void after() {
		driver.close();
	}
	
	/**
	 * This method checks if filling in correct login info brings up profile page.
	 * @throws InterruptedException 
	 */
	@Test
	public void checkIfEnteringCorrectDetailsLoadsProfilePage() throws InterruptedException {
		driver.get("https://localhost:8443/");
		
		WebElement usernameField = driver.findElement(By.id("username"));
		WebElement passwordField = driver.findElement(By.id("password"));
		
		usernameField.sendKeys("uuuuu");
		passwordField.sendKeys("uuuuu");
		
		WebElement signInButton = driver.findElement(By.id("login"));
		signInButton.click();
		
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(10))
				  .ignoring(Exception.class);
		final String expectedUrl = "https://localhost:8443/profile.html";
		final String actualUrl =  wait.until(new Function<WebDriver, String>() {
			  public String apply(WebDriver driver) {
				    return driver.getCurrentUrl();
			  }});
		assertEquals(expectedUrl, actualUrl);	
	}
	
	/**
	 * This method checks if filling in wrong login info brings up error page.
	 */
	@Test
	public void checkIfEnteringWrongDetailsLoadsErrorPage() {
		driver.get("https://localhost:8443/");
		
		WebElement usernameField = driver.findElement(By.id("username"));
		WebElement passwordField = driver.findElement(By.id("password"));
		
		usernameField.sendKeys("uutuu");
		passwordField.sendKeys("uutuu");
		
		WebElement signInButton = driver.findElement(By.id("login"));
		signInButton.click();
		
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(10))
				  .ignoring(Exception.class);
		
		final String expectedUrl = "https://localhost:8443/error.html";
		final String actualUrl =  wait.until(new Function<WebDriver, String>() {
			  public String apply(WebDriver driver) {
				    return driver.getCurrentUrl();
			  }});
		assertEquals(expectedUrl, actualUrl);	
	}
	
	/**
	 * This method checks if clicking logout brings up index page.
	 */
	@Test
	public void checkIfLogoutBringsUpIndexPage() {
		driver.get("https://localhost:8443/");
		
		WebElement usernameField = driver.findElement(By.id("username"));
		WebElement passwordField = driver.findElement(By.id("password"));
		
		usernameField.sendKeys("uuuuu");
		passwordField.sendKeys("uuuuu");
		
		WebElement signInButton = driver.findElement(By.id("login"));
		signInButton.click();
		
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(10))
				  .pollingEvery(Duration.ofSeconds(1))
				  .ignoring(Exception.class);
		WebElement logoutButton = wait.until(new Function<WebDriver, WebElement>() {
			  public WebElement apply(WebDriver driver) {
				    return driver.findElement(By.id("logout"));
				  }
		});
		
		logoutButton.click();
		
		Wait<WebDriver> wait2 = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(10))
				  .ignoring(Exception.class);
		
		final String expectedUrl = "https://localhost:8443/index.html";
		final String actualUrl =  wait2.until(new Function<WebDriver, String>() {
			  public String apply(WebDriver driver) {
				    return driver.getCurrentUrl();
			  }});
		assertEquals(expectedUrl, actualUrl);	
	}
	
	/**
	 * This method tests if trying to sign up with an existing username alerts failure.
	 */
	@Test
	public void checkIfTryingToSignUpWithAnExistingUserameAlertsError() {
		driver.get("https://localhost:8443/");
		
		WebElement signUpButton = driver.findElement(By.id("register"));
		signUpButton.click();
		
		WebElement registerUsername = driver.findElement(By.id("register-username"));
		WebElement registerEmail = driver.findElement(By.id("register-email"));
		WebElement registerPassword = driver.findElement(By.id("register-password"));
		WebElement repeatRegisterPassword = driver.findElement(By.id("repeat-register-password"));
		
		registerUsername.sendKeys("uuuuu");
		registerEmail.sendKeys("uuu@abv.bg");
		registerPassword.sendKeys("aaa");
		repeatRegisterPassword.sendKeys("aaa");
		
		WebElement confirmButton = driver.findElement(By.id("confirm-registration"));
		confirmButton.click();
		
		WebDriverWait wait = new WebDriverWait(driver, 5);
	    wait.until(ExpectedConditions.alertIsPresent());
		
		final String actualAlert = driver.switchTo().alert().getText();
		final String expectedAlert = "Registration failed!";
		
		driver.switchTo().alert().accept();

		assertEquals(expectedAlert, actualAlert);
	}
	
	/**
	 * This method tests if trying to sign up with an different passwords alerts failure.
	 */
	@Test
	public void checkIfTryingToSignUpWithDifferentPasswordsAlertsError() {
		driver.get("https://localhost:8443/");
		
		WebElement signUpButton = driver.findElement(By.id("register"));
		signUpButton.click();
		
		WebElement registerUsername = driver.findElement(By.id("register-username"));
		WebElement registerEmail = driver.findElement(By.id("register-email"));
		WebElement registerPassword = driver.findElement(By.id("register-password"));
		WebElement repeatRegisterPassword = driver.findElement(By.id("repeat-register-password"));
		
		registerUsername.sendKeys("uuauu");
		registerEmail.sendKeys("uuu@abv.bg");
		registerPassword.sendKeys("aaa");
		repeatRegisterPassword.sendKeys("aba");
		
		WebElement confirmButton = driver.findElement(By.id("confirm-registration"));
		confirmButton.click();
		
		WebDriverWait wait = new WebDriverWait(driver, 5);
	    wait.until(ExpectedConditions.alertIsPresent());
		
		final String actualAlert = driver.switchTo().alert().getText();
		final String expectedAlert = "Passwords do not match!";
		
		driver.switchTo().alert().dismiss();

		assertEquals(expectedAlert, actualAlert);
	}

}
