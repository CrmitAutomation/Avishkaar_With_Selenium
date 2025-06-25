package testcases;

import org.testng.annotations.Test;

import base.baseTest;
import pages.logoutPage;

public class logoutTest extends baseTest {

	@Test
	public void testLogout() throws InterruptedException {

		// Create LogoutPage object
		logoutPage logoutPage = new logoutPage(driver);

		// Perform login steps
		logoutPage.clickProfileIcon();
		logoutPage.clickLogoutButton();

		// Confirmation
		System.out.println("Successfully Logged out");
	}
}