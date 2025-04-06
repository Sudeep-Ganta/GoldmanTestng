package com.goldman.selenium;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.WebDriverWait;

import UnitTest.Manager_Update;

import org.openqa.selenium.support.ui.ExpectedConditions;

public class Manager {

	private WebDriver driver;
	private WebDriverWait wait;
	private Properties properties;

	// Locators for Manager page elements
	private By entityNameField = By.xpath("//input[@id='employerName']");
	private By longNameField = By.xpath("//input[@id='dbaName']");
	private By saveButton = By.xpath("//button[@title='Save (Alt + S)']");
	private By entityCodeField = By.xpath("//input[@id='employerCode']"); // Assuming entity code is displayed after creation
	private By LOBSearch= By.xpath("//input[@id='v3-widget-SEARCH_ATTRIBUTE.VC_SEARCH_TEXT-v3textfield-input']");
	private By worldButton= By.xpath("//div[text()='World']");
	private By editButton = By.xpath("//button[@title='Edit (Alt + R)']");
	

	// Constructor to initialize WebDriver
	public Manager(WebDriver driver) {
		this.driver = driver;
		this.properties = loadProperties();
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	private Properties loadProperties() {
		/*Properties properties = new Properties();
		try (FileInputStream input = new FileInputStream("D:\\Users\\SHGanta\\eclipse-workspace\\Goldman_Testing\\target\\Config.properties")) {
			properties.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		int variable;
		Properties properties = new Properties();
	    String configFilePath = "D:\\Users\\SHGanta\\eclipse-workspace\\Goldman_Testing\\target\\Config.properties";

	    try (FileInputStream in = new FileInputStream(configFilePath)) {
	        // Load the properties file
	        properties.load(in);
	        
	        // Check if the property exists before reading
	        String variableValue = properties.getProperty("variable");
	        if (variableValue == null || variableValue.isEmpty()) {
	            System.out.println("Property 'variable' not found in config file. Setting default value to 0.");
	            variable = 0; // If not found, default to 0
	        } else {
	            try {
	                variable = Integer.parseInt(variableValue);
	                System.out.println("Loaded variable value: " + variable);  // Debug log
	            } catch (NumberFormatException e) {
	                // If the value cannot be parsed, set to 0 and log an error
	                System.out.println("Invalid value for 'variable' in the properties file. Using default value of 0.");
	                variable = 0;
	            }
	        }

	        // Increment the variable
	        variable++;
	        
	        // Log the updated value before saving
	        System.out.println("Updated variable value to: " + variable);

	        // Update the properties file with the new value
	        properties.setProperty("variable", Integer.toString(variable));
	        
	        // Write the updated properties back to the file
	        try (FileOutputStream out = new FileOutputStream(configFilePath)) {
	            properties.store(out, null);
	            System.out.println("Properties file updated successfully.");
	        }

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		return properties;
	}

	// Method to retrieve the managerEntityName from the properties file
	private String getEntityName() {
		String managerEntityName ="GoldmanManagerUnit"+properties.getProperty("variable");
		return managerEntityName;
	}
	
	// Method to retrieve the managerEntityName from the properties file
	private String getUpdateEntityName() {
		String  variable = properties.getProperty("variable");
		int value= Integer.parseInt(variable)-1;
		String managerEntityName ="GoldmanManagerUnit"+value;
		return managerEntityName;
	}

	// Method to retrieve the managerLongName from the properties file
	private String getLongName() {
		String managerLongName ="GoldmanManagerUnit"+properties.getProperty("variable");
		managerLongName.toUpperCase();
		return managerLongName;
	}

	// Method to enter managerEntityName
	public void enterManagerEntityName(String managerEntityName) {
		WebElement entitynameElement = driver.findElement(entityNameField);
		entitynameElement.sendKeys(managerEntityName);
	}
	
	// Method to enter managerEntityName
		public void updateManagerEntityName(String managerEntityName) {
			WebElement entitynameElement = driver.findElement(entityNameField);
			entitynameElement.sendKeys(managerEntityName);
		}

	// Method to enter managerLongName
	public void enterManagerLongName(String managerLongName) {
		WebElement longNameElement = driver.findElement(longNameField);
		longNameElement.sendKeys(managerLongName);
	}

	// Method to click the login button
	public void clickSaveButton() {
		driver.switchTo().defaultContent();
		WebElement saveButtonElement = driver.findElement(saveButton);
		saveButtonElement.click();
	}
	
	// Method to search in LOB
		public void LOBSearch(String searchInput) throws InterruptedException {
			driver.switchTo().defaultContent();
			WebElement LOBSearchElement = driver.findElement(LOBSearch);
			LOBSearchElement.sendKeys(searchInput);
			LOBSearchElement.sendKeys(Keys.ENTER);
			Thread.sleep(3000);
		}
		
		// Method to click the world button
		public void clickWorldButton() throws InterruptedException {
			driver.switchTo().defaultContent();
			WebElement worldButtonElement = driver.findElement(worldButton);
			worldButtonElement.click();
			Thread.sleep(3000);
		}

	public void createManager() throws InterruptedException {
		String managerEntityName = getEntityName();
		String managerLongName = getLongName();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		WebDriverWait explicit = new WebDriverWait(driver, Duration.ofSeconds(30));
		explicit.until(ExpectedConditions.invisibilityOfElementLocated(By.id("bvt-loading-mask-id")));

		clickWorldButton();
		driver.findElement(By.xpath("//button[@title='New (Alt + N)']")).click();
		driver.findElement(By.xpath("//span[normalize-space()='External Manager']")).click();
		Thread.sleep(3000);
		driver.switchTo().frame("tapestry");
		enterManagerEntityName(managerEntityName);
		enterManagerLongName(managerLongName);
		clickSaveButton();
		Thread.sleep(5000);
		driver.switchTo().frame("tapestry");
		Thread.sleep(5000);
		System.out.println("Newly created entity code: " + retrieveGeneratedEntityCodeFromApp());

	}
	
	public void updateManager() throws InterruptedException {
		String updateEntityName= getUpdateEntityName();
		//String timestamp = getCurrentTimestamp();
		WebDriverWait explicit = new WebDriverWait(driver, Duration.ofSeconds(30));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		clickWorldButton();
		LOBSearch(updateEntityName);
		//Manager_Update.CaptureSS(driver, "EditButton_"+timestamp);
		explicit.until(ExpectedConditions.invisibilityOfElementLocated(By.id("bvt-loading-mask-id")));

		// Locate the Edit button
        WebElement editButtonElement = driver.findElement(editButton);

        driver.switchTo().defaultContent();
        //To avoid element interruption
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", editButtonElement);
		Thread.sleep(3000);
		driver.findElement(By.linkText("Profile")).click();
		driver.switchTo().frame("tapestry");
		//Manager_Update.CaptureSS(driver, "managerScreen_"+timestamp);
		driver.findElement(entityNameField).clear();;
		updateManagerEntityName(updateEntityName);
		driver.switchTo().defaultContent();
		clickSaveButton();
		//addContacts(); //add contacts
		//addAddress(); //add address
		driver.switchTo().frame("tapestry");
		Thread.sleep(5000);
		//Manager_Update.CaptureSS(driver, "newly updatedmanager_"+timestamp);
		String ManagerName = driver.findElement(By.xpath("//input[@id='employerName']")).getAttribute("value");
		String EntityCode = driver.findElement(By.xpath("//input[@id='employerCode']")).getAttribute("value");
		Boolean VerifyManagerName = updateEntityName.equals(ManagerName);
		if (VerifyManagerName) {
			System.out.println("Successfully updated manager entity");
			System.out.println("EntityName = " + updateEntityName);
			System.out.println("Entity Code =" + EntityCode);
		} else {
			System.out.println("Given data is not matching");
		}
	}

	// Simulate capturing the entity code from the application after creation
	private int retrieveGeneratedEntityCodeFromApp() {
		String EntityCode = driver.findElement(By.xpath("//input[@id='employerCode']")).getAttribute("value");;
		int entitycode = Integer.parseInt(EntityCode);
		return entitycode; // Simulate an auto-generated entity code from the application
	}

	// Update Manager (optional, based on your needs)
	/*
	 * public void updateManager() { System.out.println("Manager Updated: " +
	 * entityName); System.out.println("Entity Code: " + entityCode); // Logic for
	 * updating the manager entity in the system (e.g., database or API) }
	 * 
	 * // Verify Manager - Verify if the manager exists public void verifyManager()
	 * { System.out.println("Verifying Manager with Entity Code: " + entityCode); //
	 * Logic to verify manager in the system (e.g., search by entityCode in the DB
	 * or via API) }
	 */
	// Method to create a manager
	/*
	 * public int createManager(String entityName, String longName) {
	 * wait.until(ExpectedConditions.visibilityOfElementLocated(entityNameField)).
	 * sendKeys(entityName);
	 * wait.until(ExpectedConditions.visibilityOfElementLocated(longNameField)).
	 * sendKeys(longName);
	 * wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
	 * 
	 * // Simulate waiting for entity code to be generated
	 * wait.until(ExpectedConditions.visibilityOfElementLocated(entityCodeField));
	 * 
	 * // Capture the generated entity code WebElement entityCodeElement =
	 * driver.findElement(entityCodeField); String entityCodeText =
	 * entityCodeElement.getText(); return Integer.parseInt(entityCodeText); //
	 * Parse entity code to integer }
	 */

	// Method to verify the created manager (if necessary)
	public boolean verifyManager(int entityCode) {
		// Assuming we have some way to verify the manager (e.g., search with entity
		// code)
		// Implement verification logic here (e.g., looking up in a database, API, or
		// another UI element)
		return true;
	}
	
	public static String getCurrentTimestamp() {
        // Get current date and time
        LocalDateTime now = LocalDateTime.now();

        // Format it as yyyy-MM-dd_HH-mm-ss to avoid invalid characters
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        return now.format(formatter);
    }
}
