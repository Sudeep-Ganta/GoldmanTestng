package UnitTest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.google.common.io.Files;

public class manager_Create {

	private static int variable;

	public static void main(String[] args) throws Throwable {

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless"); // Run in headless mode (no GUI)
		options.addArguments("--disable-gpu"); // Disable GPU (common fix for
		// headless mode)

		System.setProperty("webdriver.chrome.driver",
				"D:\\Users\\SHGanta\\eclipse-workspace\\Drivers\\chromedriver.exe");
		System.setProperty("webdriver.http.factory", "jdk-http-client");
		WebDriver driver = new ChromeDriver(options);
		// ChromeDriver driver = new ChromeDriver(options);
		//WebDriver driver = new ChromeDriver();
		incrementValue();
		driver.get("https://goldmanver.v3locitydev.com/app");
		driver.manage().window().maximize();
		String EntityName = "GDManager" + variable;
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		WebDriverWait explicit = new WebDriverWait(driver, Duration.ofSeconds(30));
		explicit.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
		driver.findElement(By.id("username")).sendKeys("mary");
		driver.findElement(By.id("password")).sendKeys("123456" + Keys.ENTER);
		Thread.sleep(5000);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.findElement(By.xpath("//div[text()='World']")).click();
		Thread.sleep(3000);
		manager_Create.CaptureSS(driver, "newButton");
		driver.findElement(By.xpath("//button[@title='New (Alt + N)']")).click();
		driver.findElement(By.xpath("//span[normalize-space()='External Manager']")).click();
		Thread.sleep(3000);
		driver.switchTo().frame("tapestry");
		manager_Create.CaptureSS(driver, "managerScreen");
		driver.findElement(By.xpath("//input[@id='employerName']")).sendKeys(EntityName);
		driver.switchTo().defaultContent();
		driver.findElement(By.xpath("//i[normalize-space()='save']")).click();
		Thread.sleep(2000);
		driver.switchTo().frame("tapestry");
		Thread.sleep(5000);
		manager_Create.CaptureSS(driver, "newly createdmanager");
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
	}


}
