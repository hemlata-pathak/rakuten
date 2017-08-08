package com.rakuten.pages;

import java.math.BigDecimal;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.rakuten.configpageobjects.GetPage;
import com.rakuten.utils.ExtraUtil;

public class CartPage extends GetPage {
	
	public CartPage(WebDriver driver) {
		super(driver, "CartPage");
	}
	
	//validate Cart page header
	public void verifyUserIsOnCartPage() {
		isElementDisplayed("yourCartHeader_label");
		logMessage("Verified that the user is on the cart Page");
	}
	
	public boolean verifyProductAddedToCart(String skuId, String productQty, String productPrice) {
		return verifyProductAttributesAdded(skuId, productQty, productPrice);
	}
	
	//validate if selected product size is displayed in cart page
	public boolean verifyProductSizeDisplayed(String skuId, String productSize) {
		return element("productSize_label",skuId).getText().contains(productSize);
	}
	
	//validate if selected product quantity is displayed in cart page
	public boolean verifyProductQtyDisplayed(String skuId, String productQty){
		return element("productQuantity_textbox",skuId).getAttribute("value").equals(productQty);
	}
	
	//validate if selected product' price is displayed in cart page
	public boolean verifyProductPriceDisplayed(String skuId, String productPrice){
		return element("productPriceAmount_label",skuId).getText().contains(productPrice);
	}
	
	//get the shipping amount for a product
	public String getShippingAmountForProduct(String skuId) {
		return element("productShippingAmount_label", skuId).getText();
	}
	
	//get the total cost value for a product
	public String getTotalCostForProduct(String skuId) {
		return element("productTotalAmount_label",skuId).getText();
	}
	
	//calculate and get the discount amount for product if applicable
	public String getDiscountAmountForProduct(String skuId, String productPrice) {
		String discount = "0.00";
		try {
			WebElement element = element("productDiscountAmount_label",skuId);
			if (element.getAttribute("class").contains("hidden")) {
				logMessage("Discount is not available for this item");
				return discount;
			} else {
				String discountValue = element.findElement(By.tagName("span")).getText();
				if (discountValue.contains("%")) {
					return getPercentDiscount(productPrice, discountValue);
				} else {
					return discountValue;
				}
			}
		} catch (TimeoutException ex) {
			logMessage("Discount is not available for this item");
			return discount;
		}
	}
	
	//validate if product quantity and price values are same as in product page
	public boolean verifyProductAttributesAdded(String skuId, String productQty,
			String productPrice) {
		return  verifyProductQtyDisplayed(skuId, productQty)
				& verifyProductPriceDisplayed(skuId, productPrice);
	}
	
	//calculate the % discount value on a price
	public String getPercentDiscount(String productPrice, String discount){
		BigDecimal percentDiscount = new BigDecimal(ExtraUtil.convertPriceToDecimalFormat(productPrice)).multiply(new BigDecimal(ExtraUtil.convertPriceToDecimalFormat(discount))).divide(new BigDecimal("100"));
		return String.valueOf(percentDiscount);
	}
	
	/*method to calculate the item total for the product which shoulb be 
	itemPrice*qty + shipping - discount*/
	public String calculateSubTotalForASingleProduct(String productPrice, String productQty, String discount, String shippingAmount){
		BigDecimal productTotal = new BigDecimal(ExtraUtil.convertPriceToDecimalFormat(productPrice)).multiply(new BigDecimal(productQty));
		BigDecimal totalAfterDiscount = productTotal.subtract(new BigDecimal(ExtraUtil.convertPriceToDecimalFormat(discount)));
		BigDecimal totalAfterShipping = totalAfterDiscount.add(new BigDecimal(ExtraUtil.convertPriceToDecimalFormat(shippingAmount)));
		return String.valueOf(totalAfterShipping);
	}
	
	//Calculate cart total that would be sum of all item totals
	public String calculateCartTotal(){
		BigDecimal cartTotal = new BigDecimal(0.00);
		for(WebElement element : elements("priceAmount_list")){
			cartTotal = cartTotal.add(new BigDecimal(ExtraUtil.convertPriceToDecimalFormat(element.getText())));
		}
		return String.valueOf(cartTotal);
	}
	
	//get the value of summarized total
	public String getSummarizedTotal(){
		return ExtraUtil.convertPriceToDecimalFormat(element("summarizedTotal_label").getText());
	}
	
	//Validate if summarized total = sum of all item total
	public void verifySummarizedTotalIsCalculatedCorrectly(){
		isStringMatching(getSummarizedTotal(), calculateCartTotal());
		logMessage("verified that Summarized Total is calculated correctly");
	}
	
	//move to checkout page
	public CheckoutPage navigateToCheckoutPage(){
		click(element("goToCheckout_button"));
		logMessage("User is moved to checkout page");
		return PageFactory.initElements(driver, CheckoutPage.class);
	}
}
 