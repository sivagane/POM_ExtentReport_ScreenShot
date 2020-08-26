package util;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;



public class ExtentReportListener implements IReporter {
	private ExtentReports extent;
	TestUtil testUtil;
	
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
			String outputDirectory) {
		extent = new ExtentReports(outputDirectory + File.separator
				+ "Extent.html", true);

		for (ISuite suite : suites) {
			Map<String, ISuiteResult> result = suite.getResults();
			for (ISuiteResult r : result.values()) {
				ITestContext context = r.getTestContext();
				try {
					buildTestNodes(context.getPassedTests(), LogStatus.PASS);
					buildTestNodes(context.getFailedTests(), LogStatus.FAIL);
					buildTestNodes(context.getSkippedTests(), LogStatus.SKIP);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		extent.flush();
		extent.close();
	}

	private void buildTestNodes(IResultMap tests, LogStatus status) throws IOException {
		ExtentTest test;

		if (tests.size() > 0) {
			for (ITestResult result : tests.getAllResults()) {
				test = extent.startTest(result.getMethod().getMethodName());

				test.setStartedTime(getTime(result.getStartMillis()));
				test.setEndedTime(getTime(result.getEndMillis()));
				
				for (String group : result.getMethod().getGroups())
					test.assignCategory(group);

				if (result.getThrowable() != null) {
					test.log(status, result.getThrowable());
					//Check if the test case failed or was skipped and then attach the appropriate screenshot
					//using getAttribute() to the test case resut in Extent Report
					if(result.getStatus()==result.FAILURE || result.getStatus()==result.SKIP) {
						String screenshotPath=(String) result.getAttribute("screenshotPath");
						test.log(status, test.addScreenCapture(screenshotPath));
					}
				} else {
					test.log(status, "Test " + status.toString().toLowerCase()
							+ "ed");
				}

				extent.endTest(test);
			}
		}
	}

	private Date getTime(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}

}
