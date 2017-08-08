package com.rakuten.pages;

import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.rakuten.configpageobjects.GetPage;

public class LoginPage extends GetPage {
	
	public LoginPage(WebDriver driver) {
		super(driver, "LoginPage");
	}

	public void verifyUserIsOnLoginPage() {
		isElementDisplayed("loginForm_header_label");
		isElementDisplayed("username_textbox");
		isElementDisplayed("password_textbox");
		logMessage("Verified that the user is on the Login Page");
	}
	
	public MyAccountPage loginToApplication(JSONObject userData){
		element("username_textbox").clear();
		element("username_textbox").sendKeys((String) userData.get("emailId"));
		element("password_textbox").clear();
		element("password_textbox").sendKeys((String) userData.get("password"));
		click(element("login_button"));
		logMessage("User submitted the emailId and password as "+(String) userData.get("emailId") +" and "+ (String) userData.get("password"));
		return PageFactory.initElements(driver, MyAccountPage.class);
	}
	
	public void verifyUserLogoutSueccessfully(String logoutMsg){
		isElementDisplayed("loginMessages_label", logoutMsg);
		verifyUserIsOnLoginPage();
	}
	
	public void verifyErrorMessageDisplayedForInvalidLoginData(String errorMessage) {
		isElementDisplayed("loginMessages_label", errorMessage);
		verifyUserIsOnLoginPage();
	}
	
	public void verifyErrorMessageDisplayedForEmptyEmailId(String errorMessage){
		verifyElementText("blankEmailMessage_label", errorMessage);
	}

}
