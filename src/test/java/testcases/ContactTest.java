package testcases;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

public class ContactTest {

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
	public void addContact() throws InterruptedException {
		driver.findElement(By.name("username")).sendKeys("batchautomation");
		driver.findElement(By.name("password")).sendKeys("Test@12345");
		driver.findElement(By.xpath("//input[@class='btn btn-small']")).click();
		Thread.sleep(3000);
		driver.switchTo().frame("mainpanel");
		Actions action = new Actions(driver);
		WebElement Contacts = driver.findElement(By.xpath("//a[text()='Contacts']"));
		action.moveToElement(Contacts).build().perform();
		driver.findElement(By.xpath("//a[text()='New Contact']")).click();
		Thread.sleep(3000);
		driver.findElement(By.id("first_name")).sendKeys("Siva");
		driver.findElement(By.id("surname")).sendKeys("Ganesh");
		driver.findElement(By.id("f_trigger_c_birthday")).click();
		Thread.sleep(3000);
		WebElement year = driver.findElement(By.xpath("(//td[@class='button nav'])[1]"));
		for(int i=0 ;i<30;i++) {
			year.click();
		}
		Thread.sleep(3000);
		driver.findElement(By.xpath("//td[@class='button nav']//child::div[@xpathtest='1']")).isEnabled();
		
		driver.findElement(By.xpath("(//tr[@class='daysrow']//child::td[text()='8'])[3]")).click();
		driver.findElement(By.xpath("//td[@align='center'  and  @valign='top' and @colspan='2']//child::input[@value='Save']")).click();
		Thread.sleep(3000);
		boolean button = driver.findElement(By.xpath("//input[@type='button' and @value='Previous']")).isDisplayed();
		Assert.assertTrue(button);
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
