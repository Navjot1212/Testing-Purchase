package com.NavjotSingh.AssignmentWeek16;

import java.time.Duration;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ValidatingMultipleWindowTitlesV2 {
	WebDriver driver;
	SoftAssert sf;
	WebDriverWait wait;

	@BeforeMethod
	public void setUp() {
		System.setProperty("webdriver.edge.driver", "C:\\Drivers\\edgedriver_win64\\msedgedriver.exe");
		driver = new EdgeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		sf = new SoftAssert();
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		driver.get("http://seleniumpractise.blogspot.com/2017/07/multiple-window-examples.html");
	}

	@Test
	public void validatingPurchase() throws InterruptedException {
		// Home page text validation
		String text = driver.findElement(By.cssSelector("h3[itemprop='name']")).getText();
		sf.assertEquals(text, "Multiple window examples", "Text did not matched");

		// Validating Google title page
		String parentHandle = driver.getWindowHandle();
		driver.findElement(By.cssSelector("div[class = 'post-body entry-content'] a:first-of-type")).click();
		Set<String> allHandles = driver.getWindowHandles();
		String googleHanlde = getChildHandle(allHandles, parentHandle);
		driver.switchTo().window(googleHanlde);
		sf.assertEquals(driver.getTitle(), "Google", "Page title did not Match");

		// Validating Facebook Title page
		driver.switchTo().window(parentHandle);
		driver.findElement(By.cssSelector("div[class = 'post-body entry-content'] a:nth-of-type(2)")).click();
		allHandles = driver.getWindowHandles();
		String fbHanlde = getChildHandle(allHandles, googleHanlde);
		driver.switchTo().window(fbHanlde);
		sf.assertEquals(driver.getTitle(), "Facebook - log in or sign up", "Page title did not Match");

		// Validating Yahoo Title page
		driver.switchTo().window(parentHandle);
		driver.findElement(By.cssSelector("div[class = 'post-body entry-content'] a:nth-of-type(3)")).click();
		allHandles = driver.getWindowHandles();
		String yahooHanlde = getChildHandle(allHandles, fbHanlde);
		driver.switchTo().window(yahooHanlde);
		sf.assertEquals(driver.getTitle(),
				"Yahoo | Mail, Weather, Search, News, Finance, Sports, Shopping, Entertainment, Video",
				"Page title did not Match");

		// Validating Facebook Title page in same tab
		driver.switchTo().window(parentHandle);
		driver.findElement(By.cssSelector("div[class = 'post-body entry-content'] a:last-of-type")).click();
		sf.assertEquals(driver.getTitle(), "Facebook - log in or sign up", "Page title did not Match");

		sf.assertAll();
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	String getChildHandle(Set<String> allHandles, String parentHandle) {
		String childHandle = null;
		for (String handle : allHandles) {
			if (!handle.equals(parentHandle)) {
				childHandle = handle;
			}
		}
		return childHandle;
	}
}
