package testcases;

import java.io.IOException;

import org.testng.annotations.Test;

import base.baseTest;
import pages.loginPage;
import utils.ExcelUtils;

public class loginTest extends baseTest {

	@Test
	public void testLogin() throws InterruptedException, IOException {
		// Read credentials from Excel
		String username = ExcelUtils.getCellData("Login Details", 1, 0);
		String password = ExcelUtils.getCellData("Login Details", 1, 1);

		// Create LoginPage object
		loginPage loginPage = new loginPage(driver);

		// Perform login steps
		loginPage.enterUsername(username);
		loginPage.enterPassword(password);
		loginPage.clickLogin();

		// Confirmation message
		System.out.println("Employee logged in successful");
	}
}