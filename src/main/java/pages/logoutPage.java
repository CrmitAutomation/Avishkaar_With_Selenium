package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class logoutPage {

	WebDriver driver;

	// Constructor to initialize WebDriver
	public logoutPage(WebDriver driver) {
		this.driver = driver;
	}

	// Locators
	private By profileIcon = By.xpath("//span//img[@class='profile-icon']");
	private By logoutButton = By.xpath("//li//a[@title='Logout']");

	// Action method to perform logout
	public void clickProfileIcon() throws InterruptedException {
		driver.findElement(profileIcon).click();
		Thread.sleep(2000); // Ideally replace with WebDriverWait
	}

	public void clickLogoutButton() throws InterruptedException {
		driver.findElement(logoutButton).click();
		Thread.sleep(2000); // Wait for the logout process
	}

}
