/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rakuten.configenvironment;

import static com.rakuten.utils.DataReadWrite.getProperty;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.rakuten.utils.ResourceLoader;
import com.rakuten.utils.TestDataParser;


public class TestSessionInitiator {

	protected static WebDriver driver;
    private WebDriverFactory wdfactory;
    String seleniumserver;
    static int timeout;
    
    public static WebDriver getDriver() {
		return driver;
	}

    //intialize browser
    private void _configureBrowser() {
        driver = wdfactory.getDriver(_getSessionConfig());
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(_getSessionConfig().get("timeout")), TimeUnit.SECONDS);
    }
    
    //initialize test data by reading json files,it will take the file path key from config file
    protected TestDataParser _configureTestData(String testDataPath) {
    	TestDataParser dataParser = new TestDataParser(getProperty("./Config.properties", testDataPath));
    	return dataParser;
    }
    
    //method to initialize property files, it will take the file path key from config file
	protected Properties _configureApplicationData(String appDataFile) {
		return ResourceLoader.loadProperties(getProperty("./Config.properties", appDataFile));
	}

	//get initial config data by using Map in key value format
    private Map<String, String> _getSessionConfig() {
        String[] configKeys = {"env", "browser", "seleniumserver", "timeout", "chromedriverpath" , "iedriverpath", "gekodriverpath"};
        Map<String, String> config = new HashMap<String, String>();
        for (String string : configKeys) {
            config.put(string, getProperty("./Config.properties", string));
        }
        return config;
    }

    //Launch the application url
    public void launchApplication() {
    	 Reporter.log("The application url is :- " + _getSessionConfig().get("env"), true);
         Reporter.log("The test browser is :- " + _getSessionConfig().get("browser"), true);
         driver.get(_getSessionConfig().get("env"));
         Reporter.log("Application is launched");
    }

    //get current url
    public void getURL(String url) {
        driver.manage().deleteAllCookies();
        driver.get(url);
    }

    //close broser
    public void closeBrowserSession() {
    	if (null != driver) {
			driver.quit();
			Reporter.log("Driver is closed");
		}
    }
    
    //after executing each test method browser will be closed
    @AfterMethod
	public void afterMethod(ITestResult result) {
		closeBrowserSession();
	}
    
    //before executing each test method new session will be created
    @BeforeMethod
   	public void beforeMethod(ITestResult result) {
    	 wdfactory = new WebDriverFactory();
         _configureBrowser();
   	}
}