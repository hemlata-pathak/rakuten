package com.rakuten.test;

import java.util.Properties;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.rakuten.configenvironment.TestSessionInitiator;
import com.rakuten.pages.AddressPage;
import com.rakuten.pages.CartPage;
import com.rakuten.pages.CheckoutPage;
import com.rakuten.pages.OrderReviewPage;
import com.rakuten.pages.HomePage;
import com.rakuten.pages.PaymentPage;
import com.rakuten.pages.ProductPage;
import com.rakuten.utils.ExtraUtil;
import com.rakuten.utils.TestDataParser;

public class EndToEndTest extends TestSessionInitiator {

	@Test(testName = "testCheckoutForNewUser", description = "Verify that a new user has an option to select a product, go through checkout process and buy the product")
	public void testCheckoutForNewUser() {
		/******* Test Data ***********/
		TestDataParser addressDataParser = _configureTestData("addressDatapath");
		TestDataParser paymentDataParser = _configureTestData("paymentDatapath");
		TestDataParser productDataParser = _configureTestData("productDataPath");
		JSONObject productData = productDataParser.getProductData("bookCategoryData");
		Properties productProp = _configureApplicationData("productFilepath");
		String emailId = ExtraUtil.generateRandomEmail();
		String password = ExtraUtil.generateRandomPassword();
		/*****************************/
		// Launch the application
		launchApplication();
		HomePage homePage = new HomePage(driver);
		homePage.verifyUserIsOnHomePage();
		//Search and select the product usinf skuId
		ProductPage productPage = homePage.searchAndSelectProduct(productData);
		// Store the product attributes for further verifications
		String skuId = (String)productData.get("skuId");
		String productPrice = productPage.getProductPrice();
		// Enter the quantity and Add the product to cart
		productPage.addProductToCart(productProp.getProperty("quantity1"));
		// Navigate to cart page
		CartPage cartPage = productPage.navigateToCartPage();
		cartPage.verifyUserIsOnCartPage();
		// Verify that added product is displayed in cart page
		Assert.assertTrue(cartPage.verifyProductAddedToCart(skuId, productProp.getProperty("quantity1"), productPrice),
				"Verified that user has successfully added the product in to cart");
		Reporter.log("Verified that Product is added to cart");
		// calculate the item total for the added product
		String shippingCharges = cartPage.getShippingAmountForProduct(skuId);
		String discountAmount = cartPage.getDiscountAmountForProduct(skuId, productPrice);
		String expectedSubTotal = cartPage.calculateSubTotalForASingleProduct(productPrice, productProp.getProperty("quantity1"), discountAmount,shippingCharges);
		String actualSubTotal = cartPage.getTotalCostForProduct(skuId);
		// Verify the item total calculations are correct
		Assert.assertTrue(expectedSubTotal.equals(ExtraUtil.convertPriceToDecimalFormat(actualSubTotal)),
				"Verified that product total is calculated correctly for the item");
		Reporter.log("Verified that subtotal is calculated correctly for the item");
		cartPage.verifySummarizedTotalIsCalculatedCorrectly();
		// Navigate to checkout page
		CheckoutPage checkoutPage = cartPage.navigateToCheckoutPage();
		checkoutPage.verifyUserIsOnCheckoutPage();
		// Select the new user options and create a new user
		checkoutPage.selectNewCustomerOption().enterDetailsForNewCustomer(emailId, password);
		// Proceed to address page
		AddressPage addressPage = checkoutPage.navigateToAddressPage();
		addressPage.verifyUserIsOnAddressPage();
		// enter the billing address and accept the privacy conditions
		addressPage.enterBillingAddress(addressDataParser.getAddressData("bambergAddressData"))
				.acceptPrivacyConditions();
		// navigate to payment page
		PaymentPage paymentPage = addressPage.navigateToPaymentPage();
		paymentPage.verifyUserIsOnPaymentPage();
		// enter the credit card details and navigate to review the order
		OrderReviewPage orderReviewPage = paymentPage
				.enterCreditCardDetails(paymentDataParser.getCreditCardData("visaCardData"))
				.navigateToOrderReviewPage();
		orderReviewPage.verifyUserIsOnOrderReviewPage();
		// confirm the user details/ payment details and item total details are correct
		orderReviewPage.verifyUserDetails(emailId, addressDataParser.getAddressData("bambergAddressData"));
		orderReviewPage.verifyPaymentDetails(paymentDataParser.getCreditCardData("visaCardData"));
		orderReviewPage.verifyPriceDetails(expectedSubTotal);
		Reporter.log("Order is about to be placed for user..");
	}

}
