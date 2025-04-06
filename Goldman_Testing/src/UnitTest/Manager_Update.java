package UnitTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.common.io.Files;

public class Manager_Update {
	private static int variable;
	private static WebDriver driver;
	

	public static void main(String[] args) throws Throwable {

		ChromeOptions options = new ChromeOptions();
		//options.addArguments("--headless"); // Run in headless mode (no GUI)
		options.addArguments("--disable-gpu"); // Disable GPU (common fix for
		// headless mode)

		System.setProperty("webdriver.chrome.driver",
				"D:\\Users\\SHGanta\\eclipse-workspace\\Drivers\\chromedriver.exe");
		System.setProperty("webdriver.http.factory", "jdk-http-client");
		driver = new ChromeDriver(options);
		// ChromeDriver driver = new ChromeDriver(options);
		//WebDriver driver = new ChromeDriver();
		incrementValue();
		String timestamp = getCurrentTimestamp();
		driver.get("https://goldmanver.v3locitydev.com/app");
		driver.manage().window().maximize();
		variable = variable -1;
		String EntityName = "GDManager" + variable;
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		WebDriverWait explicit = new WebDriverWait(driver, Duration.ofSeconds(30));
		explicit.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
		driver.findElement(By.id("username")).sendKeys("mary");
		driver.findElement(By.id("password")).sendKeys("123456" + Keys.ENTER);
		Thread.sleep(5000);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebElement worldButton =driver.findElement(By.xpath("//div[text()='World']"));
		worldButton.click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@id='v3-widget-SEARCH_ATTRIBUTE.VC_SEARCH_TEXT-v3textfield-input']")).sendKeys(EntityName);
		driver.findElement(By.xpath("//input[@id='v3-widget-SEARCH_ATTRIBUTE.VC_SEARCH_TEXT-v3textfield-input']")).sendKeys(Keys.ENTER);
		Manager_Update.CaptureSS(driver, "EditButton_"+timestamp);
		explicit.until(ExpectedConditions.invisibilityOfElementLocated(By.id("bvt-loading-mask-id")));

		// Locate the Edit button
        WebElement editButton = driver.findElement(By.xpath("//button[@title='Edit (Alt + R)']"));

        driver.switchTo().defaultContent();
        // Wait until the Edit button is clickable
        //explicit.until(ExpectedConditions.elementToBeClickable(editButton));
        //Thread.sleep(3000);
       	//editButton.click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", editButton);
		Thread.sleep(3000);
		driver.findElement(By.linkText("Profile")).click();
		driver.switchTo().frame("tapestry");
		Manager_Update.CaptureSS(driver, "managerScreen_"+timestamp);
		driver.findElement(By.xpath("//input[@id='employerName']")).clear();
		driver.findElement(By.xpath("//input[@id='employerName']")).sendKeys(EntityName);
		driver.switchTo().defaultContent();
		driver.findElement(By.xpath("//i[normalize-space()='save']")).click();
		Thread.sleep(2000);
		addContacts(); //add contacts
		addAddress(); //add address
		driver.switchTo().frame("tapestry");
		Thread.sleep(5000);
		Manager_Update.CaptureSS(driver, "newly updatedmanager_"+timestamp);
		String ManagerName = driver.findElement(By.xpath("//input[@id='employerName']")).getAttribute("value");
		String EntityCode = driver.findElement(By.xpath("//input[@id='employerCode']")).getAttribute("value");
		Boolean VerifyManagerName = EntityName.equals(ManagerName);
		if (VerifyManagerName) {
			System.out.println("Successfully created manager entity");
			System.out.println("EntityName = " + EntityName);
			System.out.println("Entity Code =" + EntityCode);
		} else {
			System.out.println("Given data is not matching");
		}

		driver.quit();
		

	}
	
	public static void addContacts() throws InterruptedException {
		//WebElement contactFrame = driver.findElement(By.xpath("//button[@id='lob']");
		WebDriverWait explicit = new WebDriverWait(driver, Duration.ofSeconds(30));
		driver.switchTo().frame("tapestry");
		driver.findElement(By.xpath("//button[@title='Actions']")).click();
		driver.findElement(By.xpath("//input[@id='ADD_PERSON']")).click();
		Thread.sleep(5000);
		driver.switchTo().defaultContent();
		driver.findElement(By.xpath("//input[@id='v3widget-ADD_CONTACT_SEARCH-v3textfield-searchTerm-input']")).sendKeys("%"+Keys.ENTER);	
		
		driver.findElement(By.xpath("//td[@cellindex='0']")).click();
		WebElement nextButton = explicit.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"v3-widget-WIZARD_NEXT-v3textbutton\"]/button")));
		nextButton.click();
		
		driver.findElement(By.xpath("//*[@id=\"v3-widget-WIZARD_DONE-v3textbutton\"]/button")).click();
	}
	
	public static void addAddress() throws InterruptedException {
		//WebElement contactFrame = driver.findElement(By.xpath("//button[@id='lob']");
		Thread.sleep(5000);
		driver.switchTo().frame("tapestry");
		driver.findElement(By.xpath("//*[@id='EMPLOYER_DEMOGRAPHICS']/invisible[2]/table/tbody/tr/td[1]/div/div[2]/div/div/input")).click();
		driver.findElement(By.xpath("//input[@value='Add Correspondence']")).click();
		Thread.sleep(5000);
		driver.switchTo().defaultContent();
		driver.findElement(By.xpath("//*[@id='v3-widget-ADDRESSES.ADDRESS_1-v3textfield-input']")).sendKeys("New York Times Building");
		driver.findElement(By.xpath("//*[@id='v3-widget-ADDRESSES.ADDRESS_2-v3textfield-input']")).sendKeys("52-story skyscraper");
		driver.findElement(By.xpath("//*[@id='v3-widget-ADDRESSES.ADDRESS_3-v3textfield-input']")).sendKeys("west side of Midtown Manhattan");
		//WebElement countryWeb= driver.findElement(By.xpath("/html/body/div[16]/div/div/div[5]"));
		WebElement dropdown = driver.findElement(By.xpath("//*[@id=\"v3-widget-ADDRESSES.COUNTRY-v3combobox\"]/div/table/tbody/tr/td[3]/div[1]"));
		dropdown.click();  // Open the dropdown

		// Locate and click on the specific option inside the dropdown
		WebElement option = driver.findElement(By.xpath("//div[@class='C3LSBMC-Lb-a' and text()='United States']"));
		option.click();
		
		driver.findElement(By.xpath("//*[@id='v3-widget-ADDRESSES.POSTAL_CODE-v3textfield-input']")).sendKeys("10001"+Keys.ENTER);
		Thread.sleep(3000);
		driver.findElement(By.xpath("//div[contains(text(),'NEW YORK')][1]")).click();
		Thread.sleep(3000);
		WebElement cityText = driver.findElement(By.xpath("//*[@id='v3-widget-ADDRESSES.CITY-v3textfield-input']"));
		// Get the value of the input field (use 'value' to get the entered text)
		String actualCityValue = cityText.getAttribute("value");
		// Print the actual value of the City field for debugging
		System.out.println("Actual city value: " + actualCityValue);
		// Define the expected value for the City field
		String expectedCityValue = "NEW YORK";
		// Assert that the actual city value matches the expected value
		Assert.assertTrue("Element text is not as expected", expectedCityValue.equals(actualCityValue));
        
        WebElement countyText = driver.findElement(By.xpath("//*[@id=\"v3-widget-ADDRESSES.COUNTY-v3combobox-input\"]"));
        String actualCountyValue = countyText.getAttribute("value");
        Assert.assertTrue("Element text is not as expected", expectedCityValue.equals(actualCountyValue));
			
		driver.findElement(By.xpath("//*[@id='v3-widget-v3-widget-WorldPages_Addresses-v3page-window-ok-button-v3editbutton']/button")).click();
		driver.switchTo().defaultContent();
		driver.findElement(By.xpath("//i[normalize-space()='save']")).click();
	}
	

	public static void CaptureSS(WebDriver driver, String name) throws IOException {
		// Ensure the screenshots directory exists
		File screenshotDir = new File("D:\\Users\\SHGanta\\eclipse-workspace\\Goldman_Testing\\Screenshots\\Manager\\");
		if (!screenshotDir.exists()) {
			screenshotDir.mkdirs(); // Create the directory if it doesn't exist
		}

		// Take the screenshot
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File destinationFile = new File(screenshotDir, name + ".jpg");

		// Save the screenshot to the destination path
		Files.copy(screenshot, destinationFile);
		System.out.println("Screenshot saved to: " + destinationFile.getAbsolutePath());
	}

	private static void incrementValue() {
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
	        //variable++;
	        
	        // Log the updated value before saving
	        //System.out.println("Updated variable value to: " + variable);

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
	}
	public static String getCurrentTimestamp() {
        // Get current date and time
        LocalDateTime now = LocalDateTime.now();

        // Format it as yyyy-MM-dd_HH-mm-ss to avoid invalid characters
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        return now.format(formatter);
    }

}
