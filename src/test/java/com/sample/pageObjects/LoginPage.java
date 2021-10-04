package com.sample.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

	public LoginPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}

	@FindBy(name="uid")
	WebElement userId_txtBox;

	@FindBy(name="password")
	WebElement password_txtBox;

	@FindBy(name="btnLogin")
	WebElement login_btn;

	public void setUserName(String uname)
	{
		userId_txtBox.sendKeys(uname);
	}

	public void setPassword(String pwd)
	{
		password_txtBox.sendKeys(pwd);
	}


	public void clickSubmit()
	{
		login_btn.click();
	}
}









