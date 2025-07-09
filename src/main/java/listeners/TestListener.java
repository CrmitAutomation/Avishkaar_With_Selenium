package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.ExtentTest;

import utils.ExtentReportUtil;
import utils.ScreenshotUtils;

public class TestListener implements ITestListener {

    // Thread-safe ExtentTest for parallel execution (optional)
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = ExtentReportUtil.getReportInstance()
                .createTest(result.getMethod().getMethodName());
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().pass("Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = null;
        try {
            Object currentClass = result.getInstance();
            java.lang.reflect.Field field = result.getTestClass()
                    .getRealClass()
                    .getSuperclass()
                    .getDeclaredField("driver");
            field.setAccessible(true);
            driver = (WebDriver) field.get(currentClass);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String base64Screenshot = ScreenshotUtils.captureScreenshotBase64(driver);
            extentTest.get().fail("Test Failed: " + result.getThrowable(),
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot, "Failure_Image").build());
        } catch (Exception e) {
            extentTest.get().fail("Test Failed, but screenshot capture failed: " + result.getThrowable());
            e.printStackTrace();
        }
    }


    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.get().skip("Test Skipped: " + result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentReportUtil.flushReport();
    }
}
