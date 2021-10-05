package com.sample.base;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.sample.utilities.ReadConfig;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

	ReadConfig readconfig=new ReadConfig();

	public String baseURL=readconfig.getApplicationURL();
	public String username=readconfig.getUsername();
	public String password=readconfig.getPassword();
	public static WebDriver driver;
	public static Logger logger;

	@Parameters("browser")
	@BeforeClass
	public void Setup(String browser) {

		logger = Logger.getLogger("Sample Project");
		PropertyConfigurator.configure("Log4j.properties");

		if (browser.equalsIgnoreCase("Chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();

		} else if (browser.equalsIgnoreCase("Firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();

		} else if (browser.equalsIgnoreCase("Ie")) {
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();

		}else if (browser.equalsIgnoreCase("Opera")) {
			WebDriverManager.operadriver().setup();
			driver = new OperaDriver();

		} else {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}
		driver.manage().window().maximize();
		logger.info("URL is opened");
		driver.get(baseURL);
	}

	@AfterClass
	public void tearDown() {
		logger.info("Closing Browser");
		driver.close();
		driver.quit();
	}

	public void captureScreen(WebDriver driver, String tcName) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File target = new File(System.getProperty("user.dir") + "/Screenshots/" + tcName + ".png");
		FileUtils.copyFile(source, target);
		System.out.println("Screenshot Captured for test case : "+tcName);
	}

	public String generateRandomString()
	{
		return(RandomStringUtils.randomAlphabetic(8));
	}

	public String generateRandomNumber() {
		return (RandomStringUtils.randomNumeric(4));
	}

	public Boolean waitForTitleToPresent(int maxWaitTime, String title) {
		WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);
		return wait.until(ExpectedConditions.titleIs(title));
	}

	//user defined method created to check alert is preset or not
	public boolean waitForAlertIfPresent()
	{
		try
		{
			driver.switchTo().alert();
			return true;
		}
		catch(NoAlertPresentException e)
		{
			return false;
		}
	}

	public void acceptAlertIfPresent(){
		driver.switchTo().alert().accept();
	}

	public void dismissAlertIfPresent(){
		driver.switchTo().alert().dismiss();
	}

	public void switchToDefaultContent(){
		driver.switchTo().defaultContent();
	}

	public boolean verifyIsElementPresent(WebElement element){
		try {
			return element.isDisplayed();
		}
		catch (NoSuchElementException e){
			return  false;
		}
	}

	public boolean waitForPageLoad(WebDriver driver, int maxWaitTime) {

		for (int i = 0; i < maxWaitTime; i++) {

			if(((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete")){
				return true;
			}
		}
		return false;
	}

	public static String getBrowserNameAndVersion() {
		//Get Browser name and version.
		Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = caps.getBrowserName();
		String browserVersion = caps.getVersion();
		return browserName + " "+ browserVersion;
	}
}
