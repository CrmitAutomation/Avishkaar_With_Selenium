package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class timesheetPage {
	WebDriver driver;

	public timesheetPage(WebDriver driver) {
		this.driver = driver;
	}

	// Locators
	private By engagementTab = By.xpath("//div[@class='themeHeaderBottomRow']//ul//li[4]");
	private By myTimesheetTab = By.xpath("//h3[@data-target='team-timesheet/Team_Timesheet__c/00B1K00000A6xMEUAZ']");
	private By newButton = By.xpath("//div[@title='New']");
	private By dateRangeTextbox = By.xpath("//input[@name='Week Start Date']");
	private By dateRangeOkButton = By.xpath("//button[@title='OK']");
	private By project = By.xpath("//span[@title='CANZ']");
	private By category = By.xpath("//button[@name='Category']");
	private By categoryPicklist = By.xpath("//lightning-base-combobox-item[@data-value='QA']");
	private By subCategory = By.xpath("//button[@name='Sub-Category']");
	private By subCategoryPicklist = By.xpath("//lightning-base-combobox-item[@data-value='QA - Test Execution (Automation)']");
	private By hours = By.xpath("//input[@name='Hours']");
	private By description = By.xpath("//textarea[@name='Description']");
	private By cloneButton = By.xpath("//button[@title='Clone for the rest of the days of the week']");
	private By clonePopupOkButton = By.xpath("//button[@title='OK']");
	private By submitButton = By.xpath("//button[@title='Submit']");
	private By proceedButton = By.xpath("//button[@title='Proceed']");

	// Actions
	public void navigateToTimesheetPage() throws InterruptedException {
		driver.findElement(engagementTab).click();
		Thread.sleep(2000);
		driver.findElement(myTimesheetTab).click();
		Thread.sleep(2000);
	}

	public void startNewTimesheet(String dateRange) throws InterruptedException {
		driver.findElement(newButton).click();
		Thread.sleep(2000);
		driver.findElement(dateRangeTextbox).clear();
		Thread.sleep(1000);
		driver.findElement(dateRangeTextbox).sendKeys(dateRange);
		Thread.sleep(1000);
		driver.findElement(dateRangeOkButton).click();
		Thread.sleep(2000);
	}

	public void fillProjectCategory() throws InterruptedException {
		driver.findElement(project).click();
		Thread.sleep(2000);
		driver.findElement(category).click();
		driver.findElement(categoryPicklist).click();
		Thread.sleep(1000);
		driver.findElement(subCategory).click();
		driver.findElement(subCategoryPicklist).click();
		Thread.sleep(1000);
	}

	public void enterDay1Details(String desc) throws InterruptedException {
		driver.findElement(hours).sendKeys("8");
		driver.findElement(description).sendKeys(desc);
		Thread.sleep(1000);
	}

	public void cloneWeekAndFillDescriptions(String[] descriptions) throws InterruptedException {
		driver.findElement(cloneButton).click();
		Thread.sleep(1000);
		driver.findElement(clonePopupOkButton).click();
		Thread.sleep(2000);
		for (int i = 1; i < 5; i++) {
			String dynamicDescXPath = "(//textarea[@name='Description'])[" + (i + 1) + "]";
			driver.findElement(By.xpath(dynamicDescXPath)).clear();
			driver.findElement(By.xpath(dynamicDescXPath)).sendKeys(descriptions[i]);
			Thread.sleep(1000);
		}
	}

	public void submitTimesheet() throws InterruptedException {
		driver.findElement(submitButton).click();
		Thread.sleep(3000);
		driver.findElement(proceedButton).click();
		Thread.sleep(1000);
	}
}