package com.rakuten.pages;

import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.rakuten.configpageobjects.GetPage;

public class HomePage extends GetPage {
	
	public HomePage(WebDriver driver) {
		super(driver, "HomePage");
	}
	
	public void verifyUserIsOnHomePage() {
		isElementDisplayed("logo_img");
		isElementDisplayed("login_button");
		logMessage("Verified that the user is on the Home Page");
	}
	
	public LoginPage navigateToLoginPage(){
		element("login_button").click();
		logMessage("User is moved to login page");
		return PageFactory.initElements(driver, LoginPage.class);
	}
	
	public CategoryPage clickOnCategory(String categoryName){
		click(element("category_link", categoryName));
		logMessage(categoryName + "Category is selected from Home Page");
		return PageFactory.initElements(driver, CategoryPage.class);
	}
	
	public ProductPage searchAndSelectProduct(JSONObject productData){
		String skuId = (String)productData.get("skuId");
		element("searchItem_textbox").clear();
		element("searchItem_textbox").sendKeys(skuId);
		click(element("search_button"));
		logMessage("User has searched the product with id "+skuId);
		return PageFactory.initElements(driver, ProductPage.class);
	}
	
}
