package com.rakuten.configpageobjects;

import static com.rakuten.utils.DataReadWrite.getProperty;
import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;

import com.rakuten.utils.SeleniumWait;

/**
 *Class to perform basic operations on page elements
 * 
 */
public class BaseUi {

    WebDriver driver;
    protected SeleniumWait wait;
    private String pageName;

    protected BaseUi(WebDriver driver, String pageName) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.pageName = pageName;
        this.wait = new SeleniumWait(driver,
                Integer.parseInt(getProperty("Config.properties", "timeout")));
    }

    protected String getPageTitle() {
        return driver.getTitle();
    }

    protected void logMessage(String message) {
        Reporter.log(message, true);
    }

    protected String getCurrentURL() {
        return driver.getCurrentUrl();
    }

    //verfiy page title
    protected void verifyPageTitleExact(String expectedPagetitle) {
        wait.waitForPageTitleToContain(expectedPagetitle);
        assertEquals(getPageTitle(), expectedPagetitle, "Test Failed due to page title check on " + pageName);
        logMessage("Assertion Passed: PageTitle for " + pageName
                + " is exactly: '" + expectedPagetitle + "'");
    }

    //get element from a web element list based on the index
    protected WebElement getElementByIndex(List<WebElement> elementlist, int index) {
        return elementlist.get(index);
    }

    //get element from the list based on the text value
    protected WebElement getElementByExactText(List<WebElement> elementlist, String elementtext) {
        WebElement element = null;
        for (WebElement elem : elementlist) {
            if (elem.getText().equalsIgnoreCase(elementtext.trim())) {
                element = elem;
            }
        }
        // FIXME: handle if no element with the text is found in list
        if (element == null) {
        }
        return element;
    }

    //get element from the list based on the contained text
    protected WebElement getElementByContainsText(List<WebElement> elementlist, String elementtext) {
        WebElement element = null;
        for (WebElement elem : elementlist) {
            if (elem.getText().contains(elementtext.trim())) {
                element = elem;
            }
        }
        // FIXME: handle if no element with the text is found in list
        if (element == null) {
        }
        return element;
    }

    //switch frame
    protected void switchToFrame(WebElement element) {
        wait.waitForElementToBeVisible(element);
        driver.switchTo().frame(element);
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    //execute java script code if required
    protected void executeJavascript(String script) {
        ((JavascriptExecutor) driver).executeScript(script);
    }

    // function for mouse hover on any web element
    protected void hover(WebElement element) {
        Actions hoverOver = new Actions(driver);
        hoverOver.moveToElement(element).build().perform();
    }

    // hard Wait option : Should not be used unless no workaround is there
    protected void hardWait(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    // scroll to the element in the page
    protected void scrollDown(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView(true);", element);
    }

    //mouse hover and click to any web element
    protected void hoverClick(WebElement element) {
        Actions hoverClick = new Actions(driver);
        hoverClick.moveToElement(element).click().build().perform();
    }

    //click to element: handled wait and stale reference exception for element
    public void click(WebElement element) {
        try {
            wait.waitForElementToBeVisible(element);
            //scrollDown(element);
            element.click();
        } catch (StaleElementReferenceException ex1) {
            wait.waitForElementToBeVisible(element);
            scrollDown(element);
            element.click();
            logMessage("Clicked Element " + element + " after catching Stale Element Exception");
        } catch (Exception ex2) {
            logMessage("Element " + element + " could not be clicked! " + ex2.getMessage());
        }
    }
    
    // select value from drop down based on the value
    protected void selectByValue(WebElement element,String value) {
    	Select select = new Select(element);
    	select.selectByValue(value);
    	logMessage(value + " selected from dropdown");
    }
    
    // select value from drop down based on the index
    protected String selectByIndex(WebElement element,int index) {
    	Select select = new Select(element);
    	select.selectByIndex(index);
    	logMessage(index + " index selected from dropdown");
    	return select.getFirstSelectedOption().getText();
    }
   
 // select value from drop down based on the textß
    protected void selectByVisibleText(WebElement element,String text) {
    	Select select = new Select(element);
    	select.selectByVisibleText(text);
    	logMessage(text + " selected from dropdown");
    }
  
}