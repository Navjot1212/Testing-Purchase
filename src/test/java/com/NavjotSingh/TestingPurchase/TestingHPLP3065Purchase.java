package com.NavjotSingh.TestingPurchase;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestingHPLP3065Purchase {
	WebDriver driver;

	@BeforeMethod
	public void setUp() {
		System.setProperty("webdriver.edge.driver", "C:\\Drivers\\edgedriver_win64\\msedgedriver.exe");
		driver = new EdgeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get("https://naveenautomationlabs.com/opencart/index.php?route=account/login");
	}

	@Test
	public void validatingPurchase() throws InterruptedException {
		WebElement email = driver.findElement(By.cssSelector("form div[class='form-group']:first-of-type input"));
		WebElement password = driver.findElement(By.cssSelector("form div[class='form-group']:last-of-type input"));
		WebElement loginBtn = driver.findElement(By.cssSelector("input[type='submit']"));
		email.sendKeys("Navjot348@gmail.com");
		password.sendKeys("Navjot13");
		loginBtn.click();
		String titleOfPage = driver.getTitle();
		Assert.assertEquals(titleOfPage, "My Account", "Login failed");
		driver.findElement(By.cssSelector("ul[class='nav navbar-nav']>li:nth-of-type(2)>a")).click();
		driver.findElement(By.cssSelector("ul[class='nav navbar-nav']>li:nth-of-type(2)>div>a")).click();
		Thread.sleep(3000);
		Assert.assertEquals(driver.getTitle(), "Laptops & Notebooks", "Phones & PDAs page loading failed");
		driver.findElement(
				By.cssSelector("div[id='product-category']>div>div>div:nth-of-type(4)>div:first-of-type h4 a")).click();
		Thread.sleep(3000);
		driver.findElement(By.cssSelector("button[id='button-cart']")).click();
		Thread.sleep(3000);
		String text = driver.findElement(By.cssSelector("span[id='cart-total']")).getText();
		Assert.assertEquals(text, "1 item(s) - $122.00", "Product Not Added to Cart");
		driver.findElement(By.cssSelector("span[id='cart-total']")).click();
		driver.findElement(By.cssSelector("p[class='text-right'] a:last-of-type")).click();
		Thread.sleep(3000);
		driver.findElement(By.cssSelector("input[name='firstname']")).sendKeys("Navjot");
		driver.findElement(By.cssSelector("input[name='lastname']")).sendKeys("Singh");
		driver.findElement(By.cssSelector("input[name='address_1']")).sendKeys("621 Beam Court");
		driver.findElement(By.cssSelector("input[name='city']")).sendKeys("Milton");
		driver.findElement(By.cssSelector("input[name='postcode']")).sendKeys("L9E1L3");
		Thread.sleep(3000);
		selectElementByValue(driver.findElement(By.cssSelector("select[name='country_id']")), "38");
		selectElementByValue(driver.findElement(By.cssSelector("select[name='zone_id']")), "609");
		driver.findElement(By.cssSelector("input[value='Continue']")).click();
		driver.findElement(By.cssSelector("input[id='button-payment-address']")).click();
		WebElement delDetailsBtn = driver.findElement(By.cssSelector(
				"div[class='panel-group']>div:nth-of-type(3)>div:last-of-type form>div:first-of-type input"));
		Assert.assertTrue(delDetailsBtn.isSelected(), "Button not Selected");
		driver.findElement(By.cssSelector("input[id='button-shipping-address']")).click();
		WebElement flatRateBtn = driver.findElement(By.cssSelector("input[name='shipping_method']"));
		Assert.assertTrue(flatRateBtn.isSelected(), "Button not Selected");
		driver.findElement(By.cssSelector("textarea[name='comment']")).sendKeys("Beware of Dog");
		driver.findElement(By.cssSelector("input[id='button-shipping-method']")).click();
		WebElement CODBtn = driver.findElement(By.cssSelector("input[name='payment_method']"));
		boolean isCODBtnSelected = CODBtn.isSelected();
		if (!isCODBtnSelected) {
			CODBtn.click();
		}
		WebElement TCBtn = driver.findElement(By.cssSelector("input[name='agree']"));
		boolean isTCBtnSelected = TCBtn.isSelected();
		if (!isTCBtnSelected) {
			TCBtn.click();
		}
		driver.findElement(By.cssSelector("input[id='button-payment-method']")).click();
		Thread.sleep(3000);
		String productName = driver
				.findElement(By.cssSelector("table[class='table table-bordered table-hover'] tbody td:first-of-type a"))
				.getText();
		String quantity = driver
				.findElement(By.cssSelector("table[class='table table-bordered table-hover'] tbody td:nth-of-type(3)"))
				.getText();
		Thread.sleep(3000);
		Assert.assertEquals(productName, "HP LP3065", "Product name did not matched");
		Assert.assertEquals(quantity, "1", "Product quantity did not matched");
		driver.findElement(By.cssSelector("input[value='Confirm Order']")).click();
		Thread.sleep(3000);
		String Confirmation = driver.findElement(By.cssSelector("div[id='content'] h1")).getText();
		Assert.assertEquals(Confirmation, "Your order has been placed!", "Order Placement failed");
	}

	@AfterMethod
	public void tearDown() {
		driver.close();
	}

	public void selectElementByValue(WebElement element, String value) {
		Select sc = new Select(element);
		sc.selectByValue(value);
	}
}
