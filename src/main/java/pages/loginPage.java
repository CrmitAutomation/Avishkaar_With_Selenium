package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class loginPage {
	WebDriver driver;

	// Constructor to initialize driver
	public loginPage(WebDriver driver) {
		this.driver = driver;
	}

	// Locators
	private By usernameTextbox = By.xpath("//input[@placeholder='Username']");
	private By passwordTextbox = By.xpath("//input[@placeholder='Password']");
	private By loginButton = By.xpath("//span[contains(text(),'Log in')]");

	// Actions
	public void enterUsername(String username) throws InterruptedException {
		driver.findElement(usernameTextbox).sendKeys(username);
		Thread.sleep(2000);
	}

	public void enterPassword(String password) throws InterruptedException {
		driver.findElement(passwordTextbox).sendKeys(password);
		Thread.sleep(2000);
	}

	public void clickLogin() throws InterruptedException {
		driver.findElement(loginButton).click();
		Thread.sleep(2000);
	}
}
