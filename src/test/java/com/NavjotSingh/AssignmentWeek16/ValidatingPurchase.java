package com.NavjotSingh.AssignmentWeek16;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ValidatingPurchase {
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
		driver.get("https://www.demoblaze.com/index.html");
	}

	@Test
	public void validatingPurchase() throws InterruptedException {
		// Home Page validation
		sf.assertEquals(driver.getTitle(), "STORE", "Page title did not matched");

		// Product Selection
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h4 a[href$='4']")));
		driver.findElement(By.cssSelector("h4 a[href$='4']")).click();

		// Product validation
		String productName = driver.findElement(By.cssSelector("h2.name")).getText();
		String productPrice = driver.findElement(By.cssSelector("h3.price-container")).getText();
		sf.assertEquals(productName, "Samsung galaxy s7", "Product name did not matched");
		sf.assertEquals(productPrice, "$800 *includes tax", "Product price did not matched");

		// Product adding to cart
		driver.findElement(By.cssSelector("a[onclick='addToCart(4)']")).click();

		// Accepting alert
		wait.until(ExpectedConditions.alertIsPresent());
		driver.switchTo().alert().accept();

		// Navigating to Cart
		driver.findElement(By.cssSelector("a#cartur")).click();

		// Cart validation
		productName = driver.findElement(By.cssSelector("tr.success>td:nth-of-type(2)")).getText();
		productPrice = driver.findElement(By.cssSelector("tr.success>td:nth-of-type(3)")).getText();
		String totalPrice = driver.findElement(By.cssSelector("h3#totalp")).getText();
		sf.assertEquals(productName, "Samsung galaxy s7", "Product name did not matched");
		sf.assertEquals(productPrice, "800", "Product price did not matched");
		sf.assertEquals(totalPrice, "800", "Total price did not matched");

		// Placing Order
		driver.findElement(By.cssSelector("button[data-toggle='modal']")).click();
		// Filling billing details
		driver.findElement(By.cssSelector("input#name")).sendKeys("Navjot");
		driver.findElement(By.cssSelector("input#country")).sendKeys("Canada");
		driver.findElement(By.cssSelector("input#city")).sendKeys("Milton");
		driver.findElement(By.cssSelector("input#card")).sendKeys("456123789");
		driver.findElement(By.cssSelector("input#month")).sendKeys("07");
		driver.findElement(By.cssSelector("input#year")).sendKeys("2026");
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[onclick='purchaseOrder()']")));
		driver.findElement(By.cssSelector("button[onclick='purchaseOrder()']")).click();

		// Order confirmation validation
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[tabindex='1']")));
		String confiramtionMsg = driver.findElement(By.cssSelector("div[class^='sweet-alert'] h2")).getText();
		String actualOrderDetails = driver.findElement(By.cssSelector("div[class^='sweet-alert'] p")).getText();
		String expectedOrderDetails = "Amount: 800 USD\nCard Number: 456123789\nName: Navjot";
		sf.assertEquals(confiramtionMsg, "Thank you for your purchase!", "Order Placement Unsuccessful");
		sf.assertTrue(actualOrderDetails.contains(expectedOrderDetails), "Order details did not matched");
		driver.findElement(By.cssSelector("button[tabindex='1']")).click();

		// Home Page validation
		sf.assertEquals(driver.getTitle(), "STORE", "Page title did not matched");
		sf.assertAll();
	}

	@AfterMethod
	public void tearDown() {
		driver.close();
	}
}
