package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportUtil {

    private static ExtentReports extent;
    private static ExtentTest test;
    private static String reportPath;

    public static ExtentReports getReportInstance() {
        if (extent == null) {
            try {
                // Timestamped report for unique archive
                String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
                String reportDir = System.getProperty("user.dir") + "/reports";
                File dir = new File(reportDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                reportPath = reportDir + "/ExtentReport_" + timestamp + ".html";
                ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
                reporter.config().setDocumentTitle("Automation Test Report");
                reporter.config().setReportName("Test Execution Report");

                extent = new ExtentReports();
                extent.attachReporter(reporter);

                // Optional: Also copy this as index.html for Jenkins
                String jenkinsIndex = reportDir + "/index.html";
                Files.copy(new File(reportPath).toPath(), new File(jenkinsIndex).toPath(), StandardCopyOption.REPLACE_EXISTING);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return extent;
    }

    public static ExtentTest createTest(String testName) {
        test = getReportInstance().createTest(testName);
        return test;
    }

    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}
