package com.sample.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    public HomePage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath="//a[normalize-space()='Log out']")
    WebElement logout_link;

    @FindBy(xpath = "//a[normalize-space()='Manager']")
    public WebElement manager_link;

    public void clickLogout()
    {
        logout_link.click();
    }

}
