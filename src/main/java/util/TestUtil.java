package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

public class TestUtil {

	public static String username = "batchautomation";
	public static String password = "Test@12345";
	
	public String getScreenShot(WebDriver driver, String screenshotname) throws IOException {

		String dateVal = new SimpleDateFormat("ddMMyyyHHmmss").format(new Date());
		TakesScreenshot tk = (TakesScreenshot) driver;
		File src = tk.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "/FailedScreenshots/" + screenshotname + dateVal + ".png";
		File finaldestination = new File(destination);
		FileUtils.copyFile(src, finaldestination);
		return destination;
	}

}
