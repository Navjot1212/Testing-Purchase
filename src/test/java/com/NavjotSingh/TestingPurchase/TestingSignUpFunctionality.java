package com.NavjotSingh.TestingPurchase;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestingSignUpFunctionality {
	WebDriver driver;

	@BeforeMethod
	public void setUp() {
		System.setProperty("webdriver.edge.driver", "C:\\Drivers\\edgedriver_win64\\msedgedriver.exe");
		driver = new EdgeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://naveenautomationlabs.com/opencart/index.php?route=account/register");
	}

	@Test
	public void VerifyingSignUpFunctionality() {
		// Locating elements
		WebElement firstName = driver.findElement(By.cssSelector("fieldset[id=\"account\"]>div:nth-of-type(2) input"));
		WebElement lastName = driver.findElement(By.cssSelector("fieldset[id=\"account\"]>div:nth-of-type(3) input"));
		WebElement email = driver.findElement(By.cssSelector("fieldset[id=\"account\"]>div:nth-of-type(4) input"));
		WebElement telephone = driver.findElement(By.cssSelector("fieldset[id=\"account\"]>div:nth-of-type(5) input"));
		WebElement password = driver.findElement(By.cssSelector("form>fieldset:nth-of-type(2)>div input"));
		WebElement passwordConfirm = driver
				.findElement(By.cssSelector("form>fieldset:nth-of-type(2)>div:last-of-type input"));
		WebElement privacyPolicy = driver.findElement(By.cssSelector("div[class='pull-right'] input:first-of-type"));
		WebElement continueBtn = driver.findElement(By.cssSelector("div[class='pull-right'] input:last-of-type"));
		// Sending Data
		firstName.sendKeys("Navjot");
		lastName.sendKeys("Singh");
		email.sendKeys("Navjot78@gmail.com");
		telephone.sendKeys("4372174447");
		password.sendKeys("Navjot13");
		passwordConfirm.sendKeys("Navjot13");
		privacyPolicy.click();
		continueBtn.submit();
		String titleOfPage = driver.getTitle();
		Assert.assertEquals("Sign up Failed","Your Account Has Been Created!",titleOfPage);
	}

	@AfterMethod
	public void tearDown() {
		driver.close();

	}
}
