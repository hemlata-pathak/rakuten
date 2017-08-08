package com.rakuten.pages;

import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.rakuten.configpageobjects.GetPage;
import com.rakuten.utils.ExtraUtil;

public class OrderReviewPage extends GetPage {

	public OrderReviewPage(WebDriver driver) {
		super(driver, "OrderReviewPage");
	}
	
	//verify the header of order review page
	public void verifyUserIsOnOrderReviewPage() {
		isElementDisplayed("orderConfirmation_label");
		logMessage("Verified that the user is on the order review Page");
	}
	
	// verify that user details are displaying correct in review page
	public void verifyUserDetails(String emailId, JSONObject addressData) {
		//verifyElementText("userEmailId_label", emailId);
		String userName = (String) addressData.get("firstName") + " " + (String) addressData.get("lastName");
		verifyElementContainsText("userName_label", userName);
		String userAddress = (String) addressData.get("street") + " Str. " + (String) addressData.get("houseNumber")
				+ ", " + (String) addressData.get("zipCode") + " " + (String) addressData.get("city") + ", "
				+ (String) addressData.get("countryCode");
		System.out.println("Address "+userAddress);
		verifyElementContainsText("userAddress_label", userAddress);
	}
	
	//verify payment mode is displaying correct in order review page
	public void verifyPaymentDetails(JSONObject paymentData){
		verifyElementText("paymentMethod_label", (String) paymentData.get("cardType"));
	}
	
	//verify total amount is displaying correct in order review page
	public void verifyPriceDetails(String totalPrice){
		totalPrice = ExtraUtil.convertPriceToDecimalFormat(totalPrice);
		String actualPrice = ExtraUtil.convertPriceToDecimalFormat(element("totalAmount_label").getText());
		Assert.assertTrue(actualPrice.equals(totalPrice),"Assertion Failed: totalAmount_label Text is not as expected: ");
		logMessage("Assertion Passed: element totalAmount_label is visible and price is " + actualPrice);
	}
}
