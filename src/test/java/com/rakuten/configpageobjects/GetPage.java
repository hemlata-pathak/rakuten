package com.rakuten.configpageobjects;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import static com.rakuten.configpageobjects.ObjectFileReader.getELementFromFile;

/**
 *Class to get locaters from the properties defined in text files
 *and apply some basic assertions on presence of elements
 * 
 */

public class GetPage extends BaseUi {

    protected WebDriver driver;
    String pageName;

    public GetPage(WebDriver driver, String pageName) {
        super(driver, pageName);
        this.driver = driver;
        this.pageName = pageName;
    }

    //get element after it becomes visible in dom
    protected WebElement element(String elementToken) {
        return wait.waitForElementToBeVisible(driver.findElement(getLocator(elementToken)));
    }

    //get dynamic element by using replacement as the dynamic value
    protected WebElement element(String elementToken, String replacement) {
        return wait.waitForElementToBeVisible(driver.findElement(getLocator(elementToken, replacement)));
    }

    //get dynamic element list by using replacement as the dynamic value
    protected List<WebElement> elements(String elementToken, String replacement) {
        return wait.waitForElementsToBeVisible(driver.findElements(getLocator(elementToken, replacement)));
    }
    
    //compare the actual and expected strings
    protected void isStringMatching(String actual, String expected){
		Assert.assertEquals(actual, expected);
		logMessage("ACTUAL STRING : " + actual);
		logMessage("EXPECTED STRING :" + expected);
		logMessage("String compare Assertion passed.");
	}
    
    //get element list
    protected List<WebElement> elements(String elementToken) {
        String[] locator = getELementFromFile(this.pageName, elementToken);
        return driver.findElements(getLocators(locator[1].trim(), locator[2].trim()));
    }

    //validate the dynamic element's presence by using elementTextReplace as dynamic value
    protected boolean isElementDisplayed(String elementName,
            String elementTextReplace) {
        wait.waitForElementToBeVisible(element(elementName, elementTextReplace));
        boolean result = element(elementName, elementTextReplace).isDisplayed();
        assertTrue(result, "Assertion Failed: element '" + elementName
                + "with text " + elementTextReplace + "' is not displayed.");
        logMessage("Assertion Passed: element " + elementName + " with text "
                + elementTextReplace + " is displayed.");
        return result;
    }

    //validate the element's text with the expected value
    protected void verifyElementText(String elementName, String expectedText) {
		wait.waitForElementToBeVisible(element(elementName));
		assertEquals(element(elementName).getText(), expectedText, "Assertion Failed: element '" + elementName
				+ "' Text is not as expected: ");
		logMessage("Assertion Passed: element " + elementName
				+ " is visible and Text is " + expectedText);
	}
    
    //validate the element's text with the contained text
    protected void verifyElementContainsText(String elementName, String expectedText) {
		wait.waitForElementToBeVisible(element(elementName));
		Assert.assertTrue(element(elementName).getText().contains(expectedText),"Assertion Failed: element '" + elementName
				+ "' Text is not as expected: ");
		logMessage("Assertion Passed: element " + elementName
				+ " is visible and Text is " + expectedText);
	}
    
    //validate the element's presence
    protected boolean isElementDisplayed(String elementName) {
        wait.waitForElementToBeVisible(element(elementName));
        boolean result = element(elementName).isDisplayed();
        assertTrue(result, "Assertion Failed: element '" + elementName
                + "' is not displayed.");
        logMessage("Assertion Passed: element " + elementName
                + " is displayed.");
        return result;
    }

    //method to get the locater by the token value in the text files in pageObjectRepository
    protected By getLocator(String elementToken) {
        String[] locator = getELementFromFile(this.pageName, elementToken);
        return getLocators(locator[1].trim(), locator[2].trim());
    }

    //method to get the dynamic element by the token value in the text files in pageObjectRepository
    protected By getLocator(String elementToken, String replacement) {
        String[] locator = getELementFromFile(this.pageName, elementToken);
        locator[2] = locator[2].replaceAll("\\$\\{.+\\}", replacement);
        return getLocators(locator[1].trim(), locator[2].trim());
    }

    //TODO rename to distiguish between getlocator and getlocators
    private By getLocators(String locatorType, String locatorValue) {
        switch (Locators.valueOf(locatorType)) {
            case id:
                return By.id(locatorValue);
            case xpath:
                return By.xpath(locatorValue);
            case name:
                return By.name(locatorValue);
            case classname:
                return By.className(locatorValue);
            case css:
                return By.cssSelector(locatorValue);
            case linktext:
                return By.linkText(locatorValue);
            default:
                return By.id(locatorValue);
        }
    }
}