package testcases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import util.TestUtil;

public class DealsTest {

	public WebDriver driver;
	public TestUtil testutil;
	public LoginTest logintest;
	@BeforeMethod
	public void setUp() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Hp\\Downloads\\ChromeDriver\\chromedriver.exe");
		driver = new ChromeDriver();
		testutil = new TestUtil();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://www.crmpro.com/");
		Thread.sleep(3000);
	}
	
	@Test
	public void addDeal() throws InterruptedException {
		driver.findElement(By.name("username")).sendKeys("batchautomation");
		driver.findElement(By.name("password")).sendKeys("Test@12345");
		driver.findElement(By.xpath("//input[@class='btn btn-small']")).click();
		Thread.sleep(3000);
		driver.switchTo().frame("mainpanel");
		Actions action = new Actions(driver);
		WebElement Contacts = driver.findElement(By.xpath("//a[text()='Deals']"));
		action.moveToElement(Contacts).build().perform();
		driver.findElement(By.xpath("//a[text()='New Deal']")).click();
		Thread.sleep(3000);
		driver.findElement(By.id("title")).sendKeys("SivaDeal");
		driver.findElement(By.xpath("(//input[@value='Lookup'])[2]")).click();
		String parentWindow = driver.getWindowHandle();
		Set<String> allwin = driver.getWindowHandles();
		
		List<String> li = new ArrayList<String>();
		li.addAll(allwin);
		String loopupWindow = li.get(1);
		Thread.sleep(3000);
		driver.switchTo().window(loopupWindow);
		driver.findElement(By.name("search")).sendKeys("contact");
		driver.findElement(By.xpath("//input[@value='Search']")).click();
		driver.findElement(By.xpath("//a[text()='New Contact']")).click();
		Thread.sleep(3000);
		driver.switchTo().window(parentWindow);
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@type='submit' and @value='Save' and @xpathtest='1']")).click();
		Thread.sleep(3000);
		boolean img = driver.findElement(By.xpath("//input[@type='button' and @value='Shortlist']")).isDisplayed();
		Assert.assertTrue(img);
}
	
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {
		if(result.getStatus()==result.FAILURE || result.getStatus()==result.SKIP) {
			String screenshotpath = testutil.getScreenShot(driver, result.getName());
			result.setAttribute("screenshotPath", screenshotpath);
		}
		driver.quit();
	}
}