package com.rakuten.test;

import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.rakuten.configenvironment.TestSessionInitiator;
import com.rakuten.pages.HomePage;
import com.rakuten.pages.LoginPage;
import com.rakuten.pages.MyAccountPage;
import com.rakuten.utils.TestDataParser;

public class LoginTest extends TestSessionInitiator {

	@Test(testName = "testLoginWithValidData", description = "Verify User is able to login successfully with valid credential and from home page user has option to log out from the applicaion")
	public void testLoginWithValidData() {
		/******* Test Data ***********/
		TestDataParser userDataParser = _configureTestData("userDatapath");
		Properties messageProp = _configureApplicationData("messagesFilepath");
		/*****************************/
		// Launch the application
		launchApplication();
		HomePage homePage = new HomePage(driver);
		homePage.verifyUserIsOnHomePage();
		// Navigate To login page
		LoginPage loginPage = homePage.navigateToLoginPage();
		loginPage.verifyUserIsOnLoginPage();
		// login to the application with valid user data
		MyAccountPage accountPage = loginPage.loginToApplication(userDataParser.getUserData("validUserData"));
		// verify that user is able to login and navigates to myaccount page
		accountPage.verifyUserIsOnMyAccountPage();
		// verify that user is able to view his/her first name in my account page
		Assert.assertTrue(accountPage
				.verifyUserNameDisplayedMyAccountPage(userDataParser.getUserDataValue("validUserData", "firstName")));
		// logout from the application
		accountPage.logoutFromApplication();
		// verify that user is able to logout from the application and navigates to login page
		loginPage.verifyUserLogoutSueccessfully(messageProp.getProperty("signedOutMsg"));
	}

	@Test(testName = "testLoginWithInvalidData", description = "Verify user should not be able to login incorrect/invalid credentials and proper error message should displayed")
	public void testLoginWithInvalidData() {
		/******* Test Data ***********/
		TestDataParser userDataParser = _configureTestData("userDatapath");
		Properties messageProp = _configureApplicationData("messagesFilepath");
		/*****************************/
		// Launch the application
		launchApplication();
		HomePage homePage = new HomePage(driver);
		homePage.verifyUserIsOnHomePage();
		// Navigate To login page
		LoginPage loginPage = homePage.navigateToLoginPage();
		loginPage.verifyUserIsOnLoginPage();
		// login to the application with invalid email ID
		loginPage.loginToApplication(userDataParser.getUserData("invalidEmailData"));
		// verify that user should not be able to login and an error message should displayed
		loginPage.verifyErrorMessageDisplayedForInvalidLoginData(messageProp.getProperty("checkYourDataMsg"));
		loginPage.verifyErrorMessageDisplayedForEmptyEmailId(messageProp.getProperty("invalidEmailMsg"));
		// login to the application with incorrect password
		loginPage.loginToApplication(userDataParser.getUserData("invalidPasswordData"));
		// verify that user should not be able to login and an error message should displayed
		loginPage.verifyErrorMessageDisplayedForInvalidLoginData(messageProp.getProperty("invalidLoginMsg"));
		// login to the application with incorrect email ID and incorrect password
		loginPage.loginToApplication(userDataParser.getUserData("incorrectEmailPasswordData"));
		// verify that user should not be able to login and an error message should displayed
		loginPage.verifyErrorMessageDisplayedForInvalidLoginData(messageProp.getProperty("invalidLoginMsg"));
	}

	@Test(testName = "testLoginWithBlankData", description = "Verify user should not be able to login with blank email/password and proper error messages should displayed")
	public void testLoginWithBlankData() {
		/******* Test Data ***********/
		TestDataParser userDataParser = _configureTestData("userDatapath");
		Properties messageProp = _configureApplicationData("messagesFilepath");
		/*****************************/
		// Launch the application
		launchApplication();
		HomePage homePage = new HomePage(driver);
		homePage.verifyUserIsOnHomePage();
		// Navigate To login page
		LoginPage loginPage = homePage.navigateToLoginPage();
		loginPage.verifyUserIsOnLoginPage();
		// login to the application with blank email ID
		loginPage.loginToApplication(userDataParser.getUserData("blankEmailData"));
		// verify that user should not be able to login and proper error message should displayed
		loginPage.verifyErrorMessageDisplayedForInvalidLoginData(messageProp.getProperty("checkYourDataMsg"));
		loginPage.verifyErrorMessageDisplayedForEmptyEmailId(messageProp.getProperty("emptyEmailMsg"));
		// login to the application with blank password
		loginPage.loginToApplication(userDataParser.getUserData("blankPasswordData"));
		// verify that user should not be able to login and proper error message should displayed
		loginPage.verifyErrorMessageDisplayedForInvalidLoginData(messageProp.getProperty("invalidLoginMsg"));
		// login to the application with blank email id and blank password
		loginPage.loginToApplication(userDataParser.getUserData("blankEmailPasswordData"));
		// verify that user should not be able to login and proper error message should displayed
		loginPage.verifyErrorMessageDisplayedForInvalidLoginData(messageProp.getProperty("checkYourDataMsg"));
		loginPage.verifyErrorMessageDisplayedForEmptyEmailId(messageProp.getProperty("emptyEmailMsg"));
	}

}
