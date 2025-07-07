package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
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
                // Create timestamped report file
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

            try {
                String reportDir = System.getProperty("user.dir") + "/reports";
                File reportFile = new File(reportPath);

                // Master index file to list all historical reports
                File indexFile = new File(reportDir + "/master-index.html");

                // If the file doesn't exist, create it with header
                if (!indexFile.exists()) {
                    String header = "<html><head><title>Automation Report History</title></head><body><h2>Execution History</h2><ul>\n";
                    Files.write(indexFile.toPath(), header.getBytes(), StandardOpenOption.CREATE);
                }

                // Append this run to the master index
                String fileName = reportFile.getName();
                String link = String.format("<li><a href='%s' target='_blank'>%s</a></li>\n", fileName, fileName);
                Files.write(indexFile.toPath(), link.getBytes(), StandardOpenOption.APPEND);

                // (Optional) Copy latest report as index.html for Jenkins quick preview (still shows latest)
                File jenkinsIndex = new File(reportDir + "/index.html");
                Files.copy(reportFile.toPath(), jenkinsIndex.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Copy latest report with static name for Jenkins HTML Publisher
                File latestNamed = new File(reportDir + "/latest.html");
                Files.copy(reportFile.toPath(), latestNamed.toPath(), StandardCopyOption.REPLACE_EXISTING);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}