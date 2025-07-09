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

    // 🔁 Optionally used for file-based screenshots (not email-friendly)
    public static String captureScreenshot(WebDriver driver, String testName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String screenshotDir = System.getProperty("user.dir") + "/screenshots";
        new File(screenshotDir).mkdirs();  // Create folder if not exists

        String fileName = testName + "_" + timestamp + ".png";
        String fullPath = screenshotDir + "/" + fileName;

        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File(fullPath);
            FileUtils.copyFile(src, dest);
            return "../screenshots/" + fileName; // relative path for HTML report use
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // ✅ Base64 screenshot (embedded directly into ExtentReport HTML)
    public static String captureScreenshotBase64(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
    }
}
