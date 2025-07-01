package utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportUtil {

    private static ExtentReports extent;
    private static ExtentTest test;

    public static ExtentReports getReportInstance() {
        if (extent == null) {
            try {
                // Timestamp for folder
                String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
                String reportDir = System.getProperty("user.dir") + "/reports/" + timestamp;
                File dir = new File(reportDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                String reportPath = reportDir + "/ExtentReport.html";
                ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
                reporter.config().setDocumentTitle("Automation Test Report");
                reporter.config().setReportName("Test Execution Report");

                extent = new ExtentReports();
                extent.attachReporter(reporter);

            } catch (Exception e) {
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
