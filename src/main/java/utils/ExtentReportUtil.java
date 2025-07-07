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

                // ✅ Copy to Jenkins index.html (for latest preview)
                File jenkinsIndex = new File(reportDir + "/index.html");
                Files.copy(reportFile.toPath(), jenkinsIndex.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // ✅ Copy to build-specific folder if BUILD_NUMBER is available (in Jenkins)
                String buildNumber = System.getenv("BUILD_NUMBER");
                if (buildNumber != null) {
                    File buildDir = new File(reportDir + "/build_" + buildNumber);
                    buildDir.mkdirs();
                    File buildReport = new File(buildDir, "index.html");
                    Files.copy(reportFile.toPath(), buildReport.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }

                // ✅ Append to master-index.html
                File indexFile = new File(reportDir + "/master-index.html");
                if (!indexFile.exists()) {
                    String header = "<html><head><title>Automation Report History</title></head><body><h2>Execution History</h2><ul>\n";
                    Files.write(indexFile.toPath(), header.getBytes(), StandardOpenOption.CREATE);
                }

                String fileName = reportFile.getName();
                String link = String.format("<li><a href='%s' target='_blank'>%s</a></li>\n", fileName, fileName);
                Files.write(indexFile.toPath(), link.getBytes(), StandardOpenOption.APPEND);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
