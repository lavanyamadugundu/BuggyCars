package BuggyCarsDemo;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class BuggyAutomation {
	String Username = "abcd123@gmail.com";
	String Password = "Test@1234";
	String FirstName = "FirstName1";
	String LastName = "LastName1";
	String voteMsg = "Thank you for your vote!";
	String BuggyRatingText = "Buggy Rating";
	WebDriver driver = null;
	
	@BeforeSuite
	public void Register()
	{
		System.setProperty("webdriver.chrome.driver", "C:\\softwares\\chromedriver_win32\\chromedriver.exe");
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");   
		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://buggy.justtestit.org/");
		
		driver.findElement(By.cssSelector("a[class='btn btn-success-outline']")).click();
		driver.findElement(By.id("username")).sendKeys(Username);
		driver.findElement(By.id("firstName")).sendKeys(FirstName);
		driver.findElement(By.id("lastName")).sendKeys(LastName);
		driver.findElement(By.id("password")).sendKeys(Password);
		driver.findElement(By.id("confirmPassword")).sendKeys(Password);
		driver.findElement(By.cssSelector("[class='col-md-6'] button")).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}
	
	@BeforeTest
	public void Login()
	{
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.findElement(By.cssSelector("input[name='login']")).sendKeys(Username);
		driver.findElement(By.cssSelector("input[name='password']")).sendKeys(Password);
		driver.findElement(By.cssSelector("button[class='btn btn-success']")).click();
	}
	
	@Test
	public void Populamake() throws InterruptedException
	{
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.findElement(By.linkText(BuggyRatingText)).click();
		driver.findElement(By.cssSelector("img[title='Alfa Romeo']")).click();	
		
		Thread.sleep(3000);
	}
	
	@Test
	public void Buggyvote() throws InterruptedException
	{
		driver.findElement(By.linkText(BuggyRatingText)).click();
		Thread.sleep(5000);
		driver.findElement(By.cssSelector("img[title='Guilia Quadrifoglio']")).click();
		
		String voteText = driver.findElement(By.xpath("//div[2]/div[2]")).getText();
		if(!voteText.equalsIgnoreCase(voteMsg))
		{
			Thread.sleep(3000);
			driver.findElement(By.id("comment")).sendKeys("test vote by program");
			driver.findElement(By.cssSelector(".btn.btn-success")).click();
		}
		
		Assert.assertEquals(voteText, voteMsg);
	}
	
	@AfterSuite
	public void BuggyLogout() 
	{
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.findElement(By.linkText("Logout")).click();
	}
}
