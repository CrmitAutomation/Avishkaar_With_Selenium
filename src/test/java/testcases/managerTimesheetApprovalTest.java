package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.baseTest;
import pages.managerTimesheetPage;

public class managerTimesheetApprovalTest extends baseTest {

    @Test
    public void approveTimesheet() throws Exception {
    	
        // Initialize page object
        managerTimesheetPage timesheet = new managerTimesheetPage(driver);

        // Navigate to Approval Page
        timesheet.navigateToTimesheetsApprovalPage();

        // Open Pending for Approval list view
        timesheet.navigateToPendingforApprovalListViewPage();

        // Get record name and open the record
        timesheet.getTimesheetApprovalLink();

        // Approve the timesheet
        timesheet.ApproveTimesheetRecord();

        // Go to "All" list view to verify approval
        timesheet.navigateToAllListViewPage();

        // Verify approval status
        String actualStatus = timesheet.verifyApproval();

        // Assert the status is "Approved"
        Assert.assertEquals(actualStatus.trim(), "Approved", "Timesheet approval status did not match.");
        
        //confirmation message
        System.out.println("Timesheet is successful approved by your Manager");
    }
}
