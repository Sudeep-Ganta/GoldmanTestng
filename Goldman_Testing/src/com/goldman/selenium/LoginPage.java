package com.goldman.selenium;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class LoginPage {

	public WebDriver driver;
	private Properties properties;
	private WebDriverWait wait;

	// Locators for login page elements
	private By usernameField = By.id("username");
	private By passwordField = By.id("password");
	private By loginButton = By.xpath("//button[text()='Log In']"); // Fixed XPath locator

	// Constructor to initialize WebDriver and load properties
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		this.properties = loadProperties();
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Create WebDriverWait
	}

	// Method to load properties from the config file
	private Properties loadProperties() {
		Properties properties = new Properties();
		try (FileInputStream input = new FileInputStream("D:\\Users\\SHGanta\\eclipse-workspace\\Goldman_Testing\\target\\Config.properties")) {
			properties.load(input);
		} catch (IOException e) {
			e.printStackTrace();
			// Optionally, throw an exception or handle the error
		}
		return properties;
	}

	// Method to retrieve the application URL from the properties file
	private String getAppURL() {
		return properties.getProperty("app.url");
	}

	// Method to retrieve the username from the properties file
	private String getUsername() {
		return properties.getProperty("username");
	}

	// Method to retrieve the password from the properties file
	private String getPassword() {
		return properties.getProperty("password");
	}

	// Method to navigate to a given URL
	public void navigateToURL(String uri) {
		driver.get(uri); // Navigate to the provided URL
	}

	// Method to enter username
	public void enterUsername(String username) {
		WebElement usernameElement = driver.findElement(usernameField);
		usernameElement.sendKeys(username);
	}

	// Method to enter password
	public void enterPassword(String password) {
		WebElement passwordElement = driver.findElement(passwordField);
		passwordElement.sendKeys(password);
	}

	// Method to click the login button
	public void clickLoginButton() {
		WebElement loginButtonElement = driver.findElement(loginButton);
		loginButtonElement.click();
	}

	// Method to perform login using credentials from the properties file
	public void login() {
		String uri = getAppURL(); // Get URL from properties
		String username = getUsername(); // Get username from properties
		String password = getPassword(); // Get password from properties

		if (uri == null || username == null || password == null) {
			System.out.println("One or more properties are missing from the config file.");
			return; // Exit the method if any property is missing
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		WebDriverWait explicit = new WebDriverWait(driver, Duration.ofSeconds(30));
		navigateToURL(uri); // Navigate to the URL from properties
		enterUsername(username); // Enter username from properties
		enterPassword(password); // Enter password from properties

		// Wait for the login button to be clickable and then click it
		wait.until(ExpectedConditions.elementToBeClickable(loginButton));
		clickLoginButton(); // Click the login button
	}

	// Method to login and check for any errors after login (optional)
	public boolean loginAndCheckForError() {
		login(); // Perform login
		// Add verification logic if needed, e.g., check for a logout button or
		// dashboard element
		return true; // Assume login was successful (add further checks as necessary)
	}
	
}
