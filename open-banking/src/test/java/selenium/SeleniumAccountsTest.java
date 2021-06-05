package selenium;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

/**
 * This class tests accounts page.
 */
public class SeleniumAccountsTest {
	
WebDriver driver;
	
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
		options.addArguments("--incognito");
		options.addArguments("--ignore-certificate-errors");
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
	 * This method checks if accounts page brings up added accounts.
	 */
	@Test
	public void checkIfAccountsPageBringsUpAddedAccount() {
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
		WebElement accountsButton = wait.until(new Function<WebDriver, WebElement>() {
			  public WebElement apply(WebDriver driver) {
				    return driver.findElement(By.id("accounts-button"));
				  }
		});
		
		accountsButton.click();
		
		Wait<WebDriver> wait2 = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(10))
				  .ignoring(Exception.class);
		
		
		WebElement webElement =  wait2.until(new Function<WebDriver, WebElement>() {
			  public WebElement apply(WebDriver driver) {
				    return driver.findElement(By.id("accounts-list"));
			  }});
		assertTrue(webElement.isDisplayed());	
	}
	
	/**
	 * This method checks if accounts page correctly displays no added accounts.
	 */
	@Test
	public void checkIfAccountsPageDisplaysNoAddedAccount() {
		driver.get("https://localhost:8443/");
		
		WebElement usernameField = driver.findElement(By.id("username"));
		WebElement passwordField = driver.findElement(By.id("password"));
		
		usernameField.sendKeys("aaaaa");
		passwordField.sendKeys("aaaaa");
		
		WebElement signInButton = driver.findElement(By.id("login"));
		signInButton.click();
		
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(10))
				  .pollingEvery(Duration.ofSeconds(1))
				  .ignoring(Exception.class);
		WebElement accountsButton = wait.until(new Function<WebDriver, WebElement>() {
			  public WebElement apply(WebDriver driver) {
				    return driver.findElement(By.id("accounts-button"));
				  }
		});
		
		accountsButton.click();
		
		Wait<WebDriver> wait2 = new FluentWait<WebDriver>(driver)
				  .withTimeout(Duration.ofSeconds(10))
				  .ignoring(Exception.class);
		
		
		WebElement webElement =  wait2.until(new Function<WebDriver, WebElement>() {
			  public WebElement apply(WebDriver driver) {
				    return driver.findElement(By.id("accounts-list"));
			  }});
		assertTrue(webElement.getText().equals(""));	
	}

}
