package com.sample.testCases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.sample.base.BaseClass;
import com.sample.pageObjects.AddCustomerPage;
import com.sample.pageObjects.LoginPage;

public class TC_AddCustomerTest_003 extends BaseClass
{

	@Test
	public void addNewCustomer() throws InterruptedException, IOException
	{
		LoginPage lp=new LoginPage(driver);
		lp.setUserName(username);
		logger.info("User name is provided");
		lp.setPassword(password);
		logger.info("Password is provided");

		lp.clickSubmit();

		Thread.sleep(2000);

		if(waitForAlertIfPresent())
		{
			logger.info("Alert Handling");
			driver.switchTo().alert().accept();//close alert
			driver.switchTo().defaultContent();
		}

		AddCustomerPage addcust=new AddCustomerPage(driver);

		Thread.sleep(1000);

		addcust.clickAddNewCustomer();

		logger.info("providing customer details....");


		addcust.custName("Pavan");
		addcust.custgender("male");
		addcust.custdob("10","15","1985");
		addcust.custaddress("INDIA");
		addcust.custcity("HYD");
		addcust.custstate("AP");
		addcust.custpinno("5000074");
		addcust.custtelephoneno("987890091");

		String email=generateRandomString()+"@gmail.com";
		addcust.custemailid(email);
		addcust.custpassword("abcdef");
		addcust.custsubmit();

		logger.info("validation started....");

		boolean res=driver.getPageSource().contains("Customer Registered Successfully123!!!");

		if(res)
		{
			logger.info("Customer Registered Successfully!!!.");
			Assert.assertTrue(true);

		}
		else
		{
			logger.info("Customer Not Registered Successfully!!!.");
			captureScreen(driver,"addNewCustomer");
			Assert.fail();
		}
	}
}
