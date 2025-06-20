package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class managerTimesheetPage {
    WebDriver driver;

    public managerTimesheetPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
    private By engagementTab = By.xpath("//div[@class='themeHeaderBottomRow']//ul//li[4]");
    private By timesheetsApprovalTab = By.xpath("//h3[@data-target='timesheet-approval/Timesheet_Approval__c/00B1K00000AN3gwUAD']");
    private By listViewButton = By.xpath("//button[@data-value='Pending for Approval']");
    private By pendingForApprovalDropDown = By.xpath("//lightning-base-combobox-item[@data-value='Pending_for_Approval']");
    private By timesheetApprovalLink = By.xpath("//tbody[@class='slds-scrollable_y']//a");
    private By approveRejectButton = By.xpath("//button[contains(text(),'Approve/Reject')]");
    private By approveButton = By.xpath("//footer//button[contains(text(),'Approve')]");
    private By approveConfirmationButton = By.xpath("//footer//button[@title='Submit']");
    private By AllDropDown = By.xpath("//lightning-base-combobox-item[@data-value='All']");
    private By searchTextBox = By.xpath("//input[@placeholder='Search this list...']");
    private By approvalStatusField = By.xpath("//td[@data-label='Approval Status']");

    // Store record name for searching
    private String recordName;

    // Actions
    public void navigateToTimesheetsApprovalPage() throws InterruptedException {
        driver.findElement(engagementTab).click();
        Thread.sleep(2000);
        driver.findElement(timesheetsApprovalTab).click();
        Thread.sleep(2000);
    }

    public void navigateToPendingforApprovalListViewPage() throws InterruptedException {
        driver.findElement(listViewButton).click();
        Thread.sleep(2000);
        driver.findElement(pendingForApprovalDropDown).click();
        Thread.sleep(2000);
    }

    public String getTimesheetApprovalLink() throws InterruptedException {
        recordName = driver.findElement(timesheetApprovalLink).getText();
        Thread.sleep(2000);
        return recordName;
    }

    public void ApproveTimesheetRecord() throws InterruptedException {
        driver.findElement(approveRejectButton).click();
        Thread.sleep(2000);
        driver.findElement(approveButton).click();
        Thread.sleep(2000);
        driver.findElement(approveConfirmationButton).click();
        Thread.sleep(2000);
    }

    public void navigateToAllListViewPage() throws InterruptedException {
        driver.findElement(listViewButton).click();
        Thread.sleep(2000);
        driver.findElement(AllDropDown).click();
        Thread.sleep(2000);
    }

    public String verifyApproval() throws InterruptedException {
        driver.findElement(searchTextBox).clear();
        driver.findElement(searchTextBox).sendKeys(recordName);
        Thread.sleep(2000);
        return driver.findElement(approvalStatusField).getText();
    }
}
