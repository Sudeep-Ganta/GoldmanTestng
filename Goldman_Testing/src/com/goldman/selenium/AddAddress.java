package com.goldman.selenium;

import java.time.Duration;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddAddress {
	
	private WebDriver driver;
	private WebDriverWait wait;
	
	public AddAddress(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	public void addAddress() throws InterruptedException {
		
		driver.switchTo().defaultContent();
		driver.switchTo().frame("tapestry");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@value=\"Add\"]")).click();
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

}
