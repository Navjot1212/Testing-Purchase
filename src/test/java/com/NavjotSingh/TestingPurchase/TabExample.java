package com.NavjotSingh.TestingPurchase;

import java.time.Duration;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TabExample {
	WebDriver driver;

	@BeforeMethod
	public void setUp() {
		System.setProperty("webdriver.edge.driver", "C:\\Drivers\\edgedriver_win64\\msedgedriver.exe");
		driver = new EdgeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get("https://demoqa.com/browser-windows");
	}

	@Test
	public void validatingTabExample() {
		String parentWindowHandle = driver.getWindowHandle();
		System.out.println(parentWindowHandle);
		driver.findElement(By.cssSelector("#tabButton")).click();;
		Set<String> allWindowHandles = driver.getWindowHandles();
		for(String handle : allWindowHandles) {
			if(!handle.equals(parentWindowHandle)) {
				driver.switchTo().window(handle);
				System.out.println(handle);
			}
		}
				
	}

	@AfterMethod
	public void tearDown() {
		driver.close();
	}

}
