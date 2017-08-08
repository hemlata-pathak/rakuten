package com.rakuten.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.rakuten.configpageobjects.GetPage;

public class CheckoutPage extends GetPage {

	public CheckoutPage(WebDriver driver) {
		super(driver, "CheckoutPage");
	}

	//verify checkout page header
	public void verifyUserIsOnCheckoutPage() {
		isElementDisplayed("checkoutModeHeader_label");
		logMessage("Verified that the user is on the checkout Page");
	}

	//method to select new customer option for checkout
	public CheckoutPage selectNewCustomerOption() {
		click(element("newAccount_radiobutton"));
		logMessage("New Customer option is selected");
		return PageFactory.initElements(driver, CheckoutPage.class);
	}

	//method to enter emailid and password for a new customer
	public void enterDetailsForNewCustomer(String emailId, String password) {
		element("newCustomerEmail_textbox").sendKeys(emailId);
		logMessage("New user email is "+emailId);
		element("newCustomerPassword_textbox").sendKeys(password);
		logMessage("New user password is "+password);
		element("newCustomerConfiemPassword_textbox").sendKeys(password);
	}
	
	//move to address page
	public AddressPage navigateToAddressPage(){
		click(element("goToNext_button"));
		logMessage("User is moved to address page");
		return PageFactory.initElements(driver, AddressPage.class);
	} 
}
