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
    private static String timestamp;

    public static ExtentReports getReportInstance() {
        if (extent == null) {
            try {
                timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
                String reportDir = System.getProperty("user.dir") + "/reports";
                File dir = new File(reportDir);
                if (!dir.exists()) dir.mkdirs();

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

                // Create Jenkins-friendly version named index.html
                File jenkinsIndex = new File(reportDir + "/index.html");
                Files.copy(reportFile.toPath(), jenkinsIndex.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Create/append to master index file
                File indexFile = new File(reportDir + "/master-index.html");
                if (!indexFile.exists()) {
                    String header = "<html><head><title>Automation Report History</title></head><body><h2>Execution History</h2><ul>\n";
                    Files.write(indexFile.toPath(), header.getBytes(), StandardOpenOption.CREATE);
                }

                String fileName = reportFile.getName();
                String link = String.format("<li><a href='%s' target='_blank'>%s</a></li>\n", fileName, fileName);
                Files.write(indexFile.toPath(), link.getBytes(), StandardOpenOption.APPEND);

                // Optional: Copy to per-build folder if BUILD_NUMBER is set by Jenkins
                String buildNum = System.getenv("BUILD_NUMBER");
                if (buildNum != null) {
                    File buildDir = new File(reportDir + "/build_" + buildNum);
                    buildDir.mkdirs();
                    Files.copy(reportFile.toPath(), new File(buildDir, "index.html").toPath(), StandardCopyOption.REPLACE_EXISTING);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
