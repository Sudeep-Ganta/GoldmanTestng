package com.goldman.selenium;
import org.openqa.selenium.WebDriver;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddContacts {
	
	private WebDriver driver;
	private WebDriverWait wait;
	
	public AddContacts(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public void addContacts() throws InterruptedException {
		
		WebDriverWait explicit = new WebDriverWait(driver, Duration.ofSeconds(30));
		driver.switchTo().defaultContent();
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
	
	

	
}
