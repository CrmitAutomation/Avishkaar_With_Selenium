package utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import org.apache.commons.io.FileUtils;

public class ScreenshotUtils {

    public static String captureScreenshot(WebDriver driver, String testName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        // Keep screenshots in a separate folder
        String screenshotDir = System.getProperty("user.dir") + "/screenshots";
        new File(screenshotDir).mkdirs();  // Create folder if it doesn't exist

        String fileName = testName + "_" + timestamp + ".png";
        String fullPath = screenshotDir + "/" + fileName;

        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File(fullPath);
            FileUtils.copyFile(src, dest);

            // ✅ Return relative path from reports/ExtentReport.html to ../screenshots/
            return "../screenshots/" + fileName;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
