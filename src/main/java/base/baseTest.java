package base;

import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import utils.EmailUtils;
import utils.ExtentReportUtil;
import utils.configReader;

public class baseTest {
    public static WebDriver driver;
    public static Properties prop;
    public static ExtentReports extent;
    public static ExtentTest extentTest; // ✅ Used in Listener for logging steps/screenshots

    @BeforeSuite
    public void setUp() {
        prop = configReader.initProperties();
        String browser = prop.getProperty("browser");
        String baseUrl = prop.getProperty("baseURL");

        // Initialize driver based on browser
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

        // Initialize ExtentReports
        extent = ExtentReportUtil.getReportInstance();
    }

    @AfterSuite
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }

        // Flush the ExtentReport
        ExtentReportUtil.flushReport();

        // Send the latest report via email
        try {
            EmailUtils.sendTestReport();
        } catch (Exception e) {
            System.err.println("Email not sent: " + e.getMessage());
        }
    }
}
