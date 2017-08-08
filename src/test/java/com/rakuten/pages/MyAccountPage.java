package com.rakuten.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.rakuten.configpageobjects.GetPage;

public class MyAccountPage extends GetPage {

	public MyAccountPage(WebDriver driver) {
		super(driver, "MyAccountPage");
	}
	
	public void verifyUserIsOnMyAccountPage() {
		isElementDisplayed("myAccount_button");
		isElementDisplayed("recentOrderHeader_label");
		logMessage("Verified that the user is on the My Account Page");
	}
	
	public boolean verifyUserNameDisplayedMyAccountPage(String userName) {
		return element("accountName_link").getText().contains("Hi,") & element("accountName_link").getText().contains(userName);
	}
	
	public LoginPage logoutFromApplication(){
		click(element("accountName_link"));
		click(element("logout_button"));
		return PageFactory.initElements(driver, LoginPage.class);
	}

}
