package testcases;

import org.testng.annotations.Test;

import base.baseTest;
import pages.loginPage;
import utils.ExcelUtils;

public class managerLoginTest extends baseTest {


	  @Test
	    public void testManagerLogin() throws Exception {
		  
		  	//navigate to the community portal
		  	driver.get("https://sftd1--testingcoe.sandbox.my.site.com/CRMITCommunity/s/");
		  	
	        // Read manager credentials from Excel row 2
	        String username = ExcelUtils.getCellData("Login Details", 2, 0);
	        String password = ExcelUtils.getCellData("Login Details", 2, 1);

	        // Login using existing loginPage POM
	        loginPage login = new loginPage(driver);
	        login.enterUsername(username);
	        login.enterPassword(password);
	        login.clickLogin();

	        System.out.println("Manager logged in successful");
	    }
	}