package base;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import utils.ExtentReportUtil;
import utils.configReader;

public class baseTest {
	public static WebDriver driver;
	public static Properties prop;
	public static ExtentReports extent;
	public static ExtentTest test;

	@BeforeSuite
	public void setUp() {
		prop = configReader.initProperties();
		String browser = prop.getProperty("browser");
		String baseUrl = prop.getProperty("baseURL");

		if (browser.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		} else {
			throw new IllegalArgumentException("Browser not supported: " + browser);
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(baseUrl);
		extent = ExtentReportUtil.getReportInstance();
	}

	@BeforeMethod
	public void createTestReport(Method method) {
		test = extent.createTest(method.getName());
	}

	@AfterMethod
	public void logTestResult(ITestResult result) {
		if (result.getStatus() == ITestResult.SUCCESS) {
			test.pass("Test passed");
		} else if (result.getStatus() == ITestResult.FAILURE) {
			test.fail("Test failed: " + result.getThrowable());
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.skip("Test skipped: " + result.getThrowable());
		}
	}

	@AfterSuite
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
		ExtentReportUtil.flushReport();
	}
}