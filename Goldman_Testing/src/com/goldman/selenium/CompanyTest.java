package com.goldman.selenium;


	import org.testng.Assert;
	import org.testng.annotations.AfterClass;
	import org.testng.annotations.BeforeClass;
	import org.testng.annotations.Test;

	import com.aventstack.extentreports.ExtentReports;
	import com.aventstack.extentreports.ExtentTest;

	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.openqa.selenium.chrome.ChromeOptions;

	public class CompanyTest {

		public WebDriver driver;
		public LoginPage loginPage;
		public Company company; // Correct variable name to lowercase
		public AddContacts addContacts;
		public AddAddress addAddress;
		private ExtentTest test; // This will hold the test report for each test
	    private ExtentReports extent;

		@BeforeClass
		public void setup() {
			
			extent = ExtentReportListener.getExtentReports();
			//ChromeOptions options = new ChromeOptions();
			//options.addArguments("--headless");
			//options.addArguments("--disable-gpu");
			// Setup WebDriver and initialize the LoginPage and Manager Page objects
			System.setProperty("webdriver.chrome.driver",
					"D:\\Users\\SHGanta\\eclipse-workspace\\Drivers\\chromedriver.exe"); // Correct chromedriver path
			System.setProperty("webdriver.http.factory", "jdk-http-client");
			//driver = new ChromeDriver(options);
			driver = new ChromeDriver();
			
			

			// Initialize the LoginPage and Manager objects
			loginPage = new LoginPage(driver);
			company = new Company(driver); // Use lowercase for variable names
			addContacts= new AddContacts(driver);
			addAddress=new AddAddress(driver);

			// Maximize the window and navigate to the login page
			driver.manage().window().maximize();
		}

		@Test(priority = 1)
		public void testLogin() {
			 test = extent.createTest("testLogin");	
			// Test login functionality
			loginPage.login(); // Use credentials from properties file

			// Verify that login was successful, e.g., by checking the existence of a logout
			// button
			boolean isLoggedIn = loginPage.loginAndCheckForError(); // This method can be extended to check UI for
																	// post-login state
			Assert.assertTrue(isLoggedIn, "Login failed");
		}

		@Test(priority = 2, dependsOnMethods = "testLogin")
		public void testCreateCompany() throws InterruptedException {
			 test = extent.createTest("testCreateCompany");	
			// Test creating Company functionality
			 company.createCompany(); // Call createCompany method on the 'Company' instance

			// After creation, you can add additional checks to verify the Company creation
			// For example, checking the entity code value, if applicable
			// int entityCode = company.retrieveGeneratedEntityCodeFromApp();
			// Assert.assertTrue(entityCode > 0, "Entity code is not generated or
			// invalid.");
		}

		// Additional tests can be added for update and verify Company
		/*
		 * @Test(priority = 3, dependsOnMethods = "testCreateCompany") public void
		 * testVerifyCompany() { // Verify manager functionality (you can add more logic
		 * based on actual requirements) int entityCode =
		 * company.retrieveGeneratedEntityCodeFromApp(); boolean isCompanyVerified =
		 * company.verifyCompany(entityCode); Assert.assertTrue(isCompanyVerified,
		 * "Company verification failed"); }
		 */
		
		@Test(priority = 3, dependsOnMethods = "testLogin")
		public void testUpdateCompany() throws InterruptedException {
			test = extent.createTest("testUpdateCompany");
			company.updateCompany();
		}
		
		@Test(priority = 4)
		public void testAddContacts() throws InterruptedException {
			test = extent.createTest("testAddContacts");
			addContacts.addContacts();
			
		}
		@Test(priority = 5)
		public void testAddAddress() throws InterruptedException {
			test = extent.createTest("testAddContacts");
			addAddress.addAddress();
		}
		

		@AfterClass
		public void tearDown() {
			
			// Close the browser after tests
			if (driver != null) {
				driver.quit();
			}
			ExtentReportListener.flushReport();
		}
	


}
