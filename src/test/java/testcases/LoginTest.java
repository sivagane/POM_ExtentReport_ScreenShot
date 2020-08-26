package testcases;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import util.TestUtil;



public class LoginTest   {

	public WebDriver driver;
	public TestUtil testutil;
	@BeforeMethod
	public void setUp() throws InterruptedException, IOException {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Hp\\Downloads\\ChromeDriver\\chromedriver.exe");
		driver = new ChromeDriver();
		testutil = new TestUtil();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://www.crmpro.com/");
		Thread.sleep(3000);
	}
	
	@Test
	public void login() throws InterruptedException {
		driver.findElement(By.name("username")).sendKeys("batchautomation");
		driver.findElement(By.name("password")).sendKeys("Test@12345");
		driver.findElement(By.xpath("//input[@class='btn btn-small']")).click();
		Thread.sleep(3000);
		Assert.assertEquals(driver.getTitle(), "CRMPRO");
	}
	
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {
		if(result.getStatus()==result.FAILURE||result.getStatus()==result.SKIP) {
			String screenshotpath = testutil.getScreenShot(driver, result.getName());
			result.setAttribute("screenshotPath", screenshotpath);
		}
		driver.quit();
	}
}
