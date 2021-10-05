package com.sample.testCases;


import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.sample.base.BaseClass;
import com.sample.pageObjects.LoginPage;


public class TC_LoginTest_001 extends BaseClass
{

	@Test
	public void loginTest_001() throws IOException, InterruptedException
	{

		logger.info("URL is opened");

		LoginPage lp=new LoginPage(driver);
		lp.setUserName(username);
		logger.info("Entered username");

		lp.setPassword(password);
		logger.info("Entered password");

		lp.clickSubmit();
		Thread.sleep(3000);

		if(waitForAlertIfPresent())
		{
			logger.info("Alert Handling");
			driver.switchTo().alert().accept();//close alert
			driver.switchTo().defaultContent();
		}

		if(driver.getTitle().equals("Guru99 Bank Manager HomePage"))
		{
			logger.info("Login test passed");
			Assert.assertTrue(true);

		}
		else
		{
			logger.info("Login test failed");
			captureScreen(driver,"loginTest_001");
			Assert.fail();

		}

	}

}
