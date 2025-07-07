package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.MediaEntityBuilder;

import utils.ExtentReportUtil;
import utils.ScreenshotUtils;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        Object currentClass = result.getInstance();
        WebDriver driver = null;

        try {
            // Access the 'driver' field from superclass (baseTest)
            java.lang.reflect.Field field = result.getTestClass().getRealClass()
                                                  .getSuperclass()
                                                  .getDeclaredField("driver");
            field.setAccessible(true);
            driver = (WebDriver) field.get(currentClass);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String testName = result.getMethod().getMethodName();
        String screenshotPath = ScreenshotUtils.captureScreenshot(driver, testName);

        ExtentReportUtil.createTest(testName)
            .fail("Test Failed: " + result.getThrowable(),
                  MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentReportUtil.createTest(result.getMethod().getMethodName())
            .pass("Test Passed");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReportUtil.createTest(result.getMethod().getMethodName())
            .skip("Test Skipped: " + result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentReportUtil.flushReport();
    }
}
