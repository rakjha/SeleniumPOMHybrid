package com.sample.testCases;

import java.io.IOException;

import org.openqa.selenium.NoAlertPresentException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.sample.base.BaseClass;
import com.sample.pageObjects.LoginPage;
import com.sample.utilities.XLUtils;

public class TC_LoginDDT_002 extends BaseClass
{

	@Test(dataProvider="LoginData")
	public void loginDDT(String user,String pwd) throws InterruptedException, IOException
	{
		LoginPage lp=new LoginPage(driver);
		lp.setUserName(user);
		logger.info("user name provided");
		lp.setPassword(pwd);
		logger.info("password provided");

		lp.clickSubmit();

		Thread.sleep(3000);

		if(isAlertPresent()==true)
		{
			driver.switchTo().alert().accept();//close alert
			driver.switchTo().defaultContent();
			logger.info("Login passed");
			Assert.assertTrue(true);
		}
		else
		{
			logger.info("Login Failed");
			captureScreen(driver,"loginDDT1");
			Assert.fail();
		}


	}


	public boolean isAlertPresent() //user defined method created to check alert is presetn or not
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


	@DataProvider(name="LoginData")
	Object[][] getData() throws IOException
	{
		String path=System.getProperty("user.dir")+"/src/test/resources/testData/LoginData.xlsx";

		int rownum=XLUtils.getRowCount(path, "Sheet1");
		int colcount=XLUtils.getCellCount(path,"Sheet1",1);

		String[][] logindata =new String[rownum][colcount];

		for(int i=1;i<=rownum;i++)
		{
			for(int j=0;j<colcount;j++)
			{
				logindata[i-1][j]=XLUtils.getCellData(path,"Sheet1", i,j);//1 0
			}

		}
		return logindata;
	}

}
