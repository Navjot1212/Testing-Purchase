package com.NavjotSingh.AssignmentWeek16;

import java.time.Duration;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ValidatingWebPagesTiltes {
	WebDriver driver;
	SoftAssert sf;
	WebDriverWait wait;
	Actions action;

	@BeforeMethod
	public void setUp() {
		System.setProperty("webdriver.edge.driver", "C:\\Drivers\\edgedriver_win64\\msedgedriver.exe");
		driver = new EdgeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		sf = new SoftAssert();
		action = new Actions(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		driver.get("http://seleniumpractise.blogspot.com/2016/08/how-to-perform-mouse-hover-in-selenium.html");
	}

	@Test
	public void validatingPurchase() throws InterruptedException {
		// Home page text validation
		String text = driver.findElement(By.cssSelector("h3[itemprop='name']")).getText();
		sf.assertEquals(text, "How to perform mouse hover in Selenium Webdriver", "Text did not matched");

		// Validating Selenium page title
		String parentHandle = driver.getWindowHandle();
		action.moveToElement(driver.findElement(By.cssSelector("button.dropbtn"))).perform();
		driver.findElement(By.cssSelector("div.dropdown-content>a:first-of-type")).click();
		Set<String> allHandles = driver.getWindowHandles();
		for (String handle : allHandles) {
			if (!handle.equals(parentHandle)) {
				driver.switchTo().window(handle);
			}
		}
		sf.assertEquals(driver.getTitle(), "Selenium Webdriver Tutorial - Selenium Tutorial for Beginners",
				"Page title did not Match");

		// Validating TestNG page title
		driver.switchTo().window(parentHandle);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("button.dropbtn"))));
		action.moveToElement(driver.findElement(By.cssSelector("button.dropbtn"))).perform();
		driver.findElement(By.cssSelector("div.dropdown-content>a:nth-of-type(2)")).click();
		sf.assertEquals(driver.getTitle(), "TestNG Tutorials for Selenium Webdriver with Real Time Examples",
				"Page title did not Match");

		// Validating Appium page title
		driver.navigate().back();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("button.dropbtn"))));
		action.moveToElement(driver.findElement(By.cssSelector("button.dropbtn"))).perform();
		driver.findElement(By.cssSelector("div.dropdown-content>a:last-of-type")).click();
		sf.assertEquals(driver.getTitle(), "Complete Ultimate Appium tutorial for beginners using JAVA for Selenium",
				"Page title did not Match");
		sf.assertAll();
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}
