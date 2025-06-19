package utils;

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
			String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
			String reportPath = "reports/ExtentReport_" + timestamp + ".html";
			ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
			reporter.config().setDocumentTitle("Automation Test Report");
			reporter.config().setReportName("Test Execution Report");

			extent = new ExtentReports(); // Correct class
			extent.attachReporter(reporter);
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