package com.rakuten.utils;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TestDataParser {

	JSONParser parser;
	JSONObject jsonObject;

	public TestDataParser(String fileName) {
		parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(fileName));
			jsonObject = (JSONObject) obj;
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public JSONObject getUserData(String userType){
		JSONObject userData = (JSONObject) jsonObject.get("userData");
        JSONObject userDataObj = (JSONObject) userData.get(userType);
        return userDataObj;
	}
	
	public JSONObject getAddressData(String addressType){
		JSONObject addressData = (JSONObject) jsonObject.get("addressData");
        JSONObject adressDataObj = (JSONObject) addressData.get(addressType);
        return adressDataObj;
	}
	
	public JSONObject getProductData(String productType){
		JSONObject productData = (JSONObject) jsonObject.get("productData");
        JSONObject productDataObj = (JSONObject) productData.get(productType);
        return productDataObj;
	}
	
	public JSONObject getCreditCardData(String creditCardType){
		JSONObject paymentData = (JSONObject) jsonObject.get("paymentData");
        JSONObject creditCardDataObj = (JSONObject) paymentData.get(creditCardType);
        return creditCardDataObj;
	}
	
	public String getUserDataValue(String userType, String userDataKey){
		JSONObject userData = (JSONObject) jsonObject.get("userData");
        JSONObject userDataObj = (JSONObject) userData.get(userType);
        String userdataValue = (String)userDataObj.get(userDataKey);
        return userdataValue;
	}
	
	public String getAddressDataValue(String addressType, String addressDataKey){
		JSONObject addressData = (JSONObject) jsonObject.get("addressData");
        JSONObject adressDataObj = (JSONObject) addressData.get(addressType);
        String addressdataValue = (String)adressDataObj.get(addressDataKey);
        return addressdataValue;
	}
	
	
	
}
