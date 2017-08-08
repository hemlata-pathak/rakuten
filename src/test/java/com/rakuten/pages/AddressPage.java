package com.rakuten.pages;

import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.rakuten.configpageobjects.GetPage;
import com.rakuten.utils.DateUtil;

public class AddressPage extends GetPage {

	public AddressPage(WebDriver driver) {
		super(driver, "AddressPage");
	}

	//verify header for the address page
	public void verifyUserIsOnAddressPage() {
		isElementDisplayed("billingAddressHeader_label");
		logMessage("Verified that the user is on the address Page");
	}

	//method to enter the billing address, we need to pass the address data object as paramenter
	public AddressPage enterBillingAddress(JSONObject addressData) {
		click(element("billingAddressSaluation_dropdown"));
		click(getElementByExactText(elements("billingAddressSaluation_list"), (String) addressData.get("saluation")));
		element("billingAddressfName_textbox").sendKeys((String) addressData.get("firstName"));
		element("billingAddresslName_textbox").sendKeys((String) addressData.get("lastName"));
		element("billingAddressStreet_textbox").sendKeys((String) addressData.get("street"));
		element("billingAddressStreetNumber_textbox").sendKeys((String) addressData.get("houseNumber"));
		element("billingAddressZipCode_textbox").sendKeys((String) addressData.get("zipCode"));
		element("billingAddressCity_textbox").sendKeys((String) addressData.get("city"));
		element("billingAddressTelephone_textbox").sendKeys((String) addressData.get("telephone"));
		click(element("billingAddressCountry_dropdown"));
		click(getElementByExactText(elements("billingAddressCountry_list"), (String) addressData.get("country")));
		String userDob = (String) addressData.get("dob");
		click(element("billingAddressDay_dropdown"));
		click(getElementByExactText(elements("billingAddressDate_list"),
				String.valueOf(DateUtil.getDayFromDate(userDob))));
		click(element("billingAddressMonth_dropdown"));
		click(getElementByExactText(elements("billingAddressMonth_list"),
				DateUtil.getMonthName(DateUtil.getMonthFromDate(userDob))));
		click(element("billingAddressYear_dropdown"));
		click(getElementByExactText(elements("billingAddressYear_list"),
				String.valueOf(DateUtil.getYearFromDate(userDob))));
		logMessage("User has entered the billing address");
		return PageFactory.initElements(driver, AddressPage.class);
	}
	
	public void acceptPrivacyConditions(){
		click(element("privacyConditions_checkbox"));
		logMessage("User has accepted privacy conditions");
	}
	
	public PaymentPage navigateToPaymentPage(){
		click(element("goToNext_button"));
		logMessage("User is moved to payment page");
		return PageFactory.initElements(driver, PaymentPage.class);
	}

}
