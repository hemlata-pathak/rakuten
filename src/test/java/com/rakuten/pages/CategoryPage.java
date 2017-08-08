package com.rakuten.pages;

import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.rakuten.configpageobjects.GetPage;

public class CategoryPage extends GetPage {
	
	public CategoryPage(WebDriver driver) {
		super(driver, "CategoryPage");
	}
	
	//Verify category name is displayed in category page
	public void verifyUserIsOnCategoryPage() {
		isElementDisplayed("categoryHeader_label");
		logMessage("Verified that the user is on the Category Page");
	}
	
	//method to click on the first product for the selected category
	public ProductPage selectFirstProduct(){
		element("firstRowItem_button").click();
		logMessage("Verified that the first product is selected from a Category");
		return PageFactory.initElements(driver, ProductPage.class);
	}
	
	// method to click on the item using skuId
	public ProductPage selectProductById(JSONObject productData){
		String skuId = (String)productData.get("skuId");
		click(element("productBySkuId_Button", skuId));
		return PageFactory.initElements(driver, ProductPage.class);
	}

}
