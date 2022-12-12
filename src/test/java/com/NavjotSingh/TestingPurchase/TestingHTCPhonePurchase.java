package com.NavjotSingh.TestingPurchase;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestingHTCPhonePurchase {
	WebDriver driver;

	@BeforeTest
	public void setUp() {
		System.setProperty("webdriver.edge.driver", "C:\\Drivers\\edgedriver_win64\\msedgedriver.exe");
		driver = new EdgeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://naveenautomationlabs.com/opencart/index.php?route=account/login");
	}

	@Test(priority = 1)
	public void validatingLoginFunctionality() {
		WebElement email = driver.findElement(By.cssSelector("form div[class='form-group']:first-of-type input"));
		WebElement password = driver.findElement(By.cssSelector("form div[class='form-group']:last-of-type input"));
		WebElement loginBtn = driver.findElement(By.cssSelector("input[type='submit']"));
		email.sendKeys("Navjot78@gmail.com");
		password.sendKeys("Navjot13");
		loginBtn.click();
		String titleOfPage = driver.getTitle();
		Assert.assertEquals(titleOfPage, "My Account", "Login failed");
	}

	@Test(priority = 2)
	public void validatingPhonesAndPdaPage() {
		driver.findElement(By.cssSelector("ul[class='nav navbar-nav']>li:nth-of-type(6) a")).click();
		Assert.assertEquals(driver.getTitle(), "Phones & PDAs", "Phones & PDAs page loading failed");
		driver.findElement(
				By.cssSelector("div[id='product-category']>div>div>div:nth-of-type(2)>div:first-of-type h4 a")).click();

	}

	@Test(priority = 3)
	public void validatingHTCPage() {
		Assert.assertEquals(driver.getTitle(), "HTC Touch HD", "HTC Touch HD page loading failed");
	}

	@Test(priority = 4)
	public void validatingProductAddedToCart() throws InterruptedException {
		driver.findElement(By.cssSelector("button[id='button-cart']")).click();
		Thread.sleep(5000);
		String text = driver.findElement(By.cssSelector("span[id='cart-total']")).getText();
		Assert.assertEquals(text, "1 item(s) - $100.00", "Product Not Added to Cart");
	}

	@Test(priority = 5)
	public void validatingCheckoutPage() {
		driver.findElement(By.cssSelector("span[id='cart-total']")).click();
		driver.findElement(By.cssSelector("p[class='text-right'] a:last-of-type")).click();
		Assert.assertEquals(driver.getTitle(), "Checkout", "Checkout page loading failed");
	}

	@Test(priority = 6)
	public void validatingBillingDetails() {
		driver.findElement(By.cssSelector("input[name='firstname']")).sendKeys("Navjot");
		driver.findElement(By.cssSelector("input[name='lastname']")).sendKeys("Singh");
		driver.findElement(By.cssSelector("input[name='address_1']")).sendKeys("621 Beam Court");
		driver.findElement(By.cssSelector("input[name='city']")).sendKeys("Milton");
		driver.findElement(By.cssSelector("input[name='postcode']")).sendKeys("L9E1L3");
		selectElementByValue(driver.findElement(By.cssSelector("select[name='country_id']")), "38");
		selectElementByValue(driver.findElement(By.cssSelector("select[name='zone_id']")), "609");
		driver.findElement(By.cssSelector("input[value='Continue']")).click();
		driver.findElement(By.cssSelector("input[id='button-payment-address']")).click();

	}

	@Test(priority = 7)
	public void validatingDeliveryDetails() {
		WebElement delDetailsBtn = driver.findElement(By.cssSelector(
				"div[class='panel-group']>div:nth-of-type(3)>div:last-of-type form>div:first-of-type input"));
		Assert.assertTrue(delDetailsBtn.isSelected(), "Button not Selected");
		driver.findElement(By.cssSelector("input[id='button-shipping-address']")).click();

	}

	@Test(priority = 8)
	public void validatingDeliveryMethod() {
		WebElement flatRateBtn = driver.findElement(By.cssSelector("input[name='shipping_method']"));
		Assert.assertTrue(flatRateBtn.isSelected(), "Button not Selected");
		driver.findElement(By.cssSelector("textarea[name='comment']")).sendKeys("Beware of Dog");
		driver.findElement(By.cssSelector("input[id='button-shipping-method']")).click();

	}

	@Test(priority = 9)
	public void validatingPaymentMethod() throws InterruptedException {
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
		Assert.assertTrue(isCODBtnSelected, "Buttons not Selected");
		driver.findElement(By.cssSelector("input[id='button-payment-method']")).click();
		Thread.sleep(3000);

	}

	@Test(priority = 10)
	public void validatingConfirmOrder() throws InterruptedException {
		String productName = driver
				.findElement(By.cssSelector("table[class='table table-bordered table-hover'] tbody td:first-of-type a"))
				.getText();
		String quantity = driver
				.findElement(By.cssSelector("table[class='table table-bordered table-hover'] tbody td:nth-of-type(3)"))
				.getText();
		Assert.assertEquals(productName, "HTC Touch HD", "Product name did not matched");
		Assert.assertEquals(quantity, "1", "Product quantity did not matched");
		driver.findElement(By.cssSelector("input[value='Confirm Order']")).click();
		Thread.sleep(3000);
	}

	@Test(priority = 11)
	public void validatingOrderPlaced() {
		String Confirmation = driver.findElement(By.cssSelector("div[id='content'] h1")).getText();
		Assert.assertEquals(Confirmation, "Your order has been placed!", "Order Placement failed");
	}

	@AfterTest
	public void tearDown() {
		driver.close();
	}

	public void selectElementByValue(WebElement element, String value) {
		Select sc = new Select(element);
		sc.selectByValue(value);
	}
}
