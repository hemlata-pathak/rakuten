package com.rakuten.pages;

import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.rakuten.configpageobjects.GetPage;

public class PaymentPage extends GetPage {

	public PaymentPage(WebDriver driver) {
		super(driver, "PaymentPage");
	}
	
	//Validate the payment page header
	public void verifyUserIsOnPaymentPage() {
		isElementDisplayed("paymentPageHeader_label");
		logMessage("Verified that the user is on the payment Page");
	}
	
	//enter the credit card details: it will be passed as an Payment data json object
	public PaymentPage enterCreditCardDetails(JSONObject paymentData){
		click(element("cardType_dropdown"));
		click(getElementByExactText(elements("cardType_list"), (String) paymentData.get("cardType")));
		element("cardholderName_textbox").sendKeys((String) paymentData.get("userName"));
		element("cardNumber_textbox").sendKeys((String) paymentData.get("creditCardNumber"));
		element("cvvNumber_textbox").sendKeys((String) paymentData.get("cvvCode"));
		String expiryDate = (String) paymentData.get("expiryDate");
		click(element("cardExpiryMonth_dropdown"));
		click(getElementByExactText(elements("cardExpiryMonth_list"),expiryDate.split("-")[0]));
		click(element("cardExpiryYear_dropdown"));
		click(getElementByExactText(elements("cardExpiryYear_list"),expiryDate.split("-")[1]));
		logMessage("User has entered the credit card details");
		return PageFactory.initElements(driver, PaymentPage.class);
	}
	
	//Move to order review page
	public OrderReviewPage navigateToOrderReviewPage(){
		click(element("goToNext_button"));
		logMessage("User is moved to order review page");
		return PageFactory.initElements(driver, OrderReviewPage.class);
	}
	
	

}
