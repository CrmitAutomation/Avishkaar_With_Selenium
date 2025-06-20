package testcases;

import org.testng.annotations.Test;

import base.baseTest;
import pages.timesheetPage;
import utils.ExcelUtils;

public class timesheetTest extends baseTest {

	@Test
	public void testCreateTimesheet() throws Exception {

		// Read data
		String dateRange = ExcelUtils.getCellData("Timesheet Details", 1, 0); // A2
		String[] descriptions = new String[5];
		for (int i = 0; i < 5; i++) {
			descriptions[i] = ExcelUtils.getCellData("Timesheet Details", i + 1, 1); // B2 to B6
		}

		// navigate to timesheet
		timesheetPage timesheet = new timesheetPage(driver);
		timesheet.navigateToTimesheetPage();

		// Perform timesheet submission steps
		timesheet.startNewTimesheet(dateRange);
		timesheet.fillProjectCategory();
		timesheet.enterDay1Details(descriptions[0]);
		timesheet.cloneWeekAndFillDescriptions(descriptions);
		timesheet.submitTimesheet();
		Thread.sleep(10000);

		// Confirmation
		System.out.println("Timesheet submitted successfully");
	}
}