package com.rakuten.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.rakuten.configpageobjects.GetPage;

public class ProductPage  extends GetPage {
	
	public ProductPage(WebDriver driver) {
		super(driver, "ProductPage");
	}
	
	//validate if user is on product page and able to view name/quantity elements for an item
	public void verifyUserIsOnProductPage() {
		isElementDisplayed("productName_label");
		isElementDisplayed("size_dropdown");
		isElementDisplayed("quantity_textbox");
		logMessage("Verified that the user is on the Category Page");
	}
	
	//method to select size by giving the size data
	public void selectSizeForProduct(String size){
		selectByVisibleText(element("size_dropdown"),size);
	}
	
	//method to select the first size from the dropdown
	public String selectFirstSizeForProduct(){
		return selectByIndex(element("size_dropdown"),1);
	}
	
	//get the product name from the product page
	public String getProductName(){
		return element("productName_label").getAttribute("innerHTML");
	}
	
	//get the product price from the product page
	public String getProductPrice(){
		return element("productPrice_label").getText();
	}
	
	// method to enter the quanity
	public void enterQty(String qty){
		element("quantity_textbox").clear();
		element("quantity_textbox").sendKeys(qty);
	}
	
	//enter the quantity and add the product to cart
	public ProductPage addProductToCart(String qty){
		//selectFirstSizeForProduct();
		enterQty(qty);
		click(element("addToCart_button"));
		logMessage("Product is added to cart");
		return PageFactory.initElements(driver, ProductPage.class);
	}
	
	/*method to verify that user gets a confirmation after adding the product in to cart
	and he has option to continue shopping or proceed for checkout*/
	public void verifyProductAdded(String productAddedMsg){
		verifyElementText("productAddedMsg_label", productAddedMsg);
		isElementDisplayed("continueShopping_button");
		isElementDisplayed("moveToCartPage_button");
	}
	
	//Move to cart page
	public CartPage navigateToCartPage(){
		click(element("moveToCartPage_button"));
		logMessage("User is moved to cart page");
		return PageFactory.initElements(driver, CartPage.class);
	}

}
