/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rakuten.configenvironment;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;

public class WebDriverFactory {

	private static String browser;
	private static DesiredCapabilities capabilities = new DesiredCapabilities();

	//launch browser based on the value in config file
	public WebDriver getDriver(Map<String, String> seleniumconfig) {
		browser = seleniumconfig.get("browser").toString();
		System.out.println("browser "+browser);
		if (seleniumconfig.get("seleniumserver").toString().equalsIgnoreCase("local")) {
			if (browser.equalsIgnoreCase("firefox")) {
				return getFirefoxDriver(seleniumconfig.get("gekodriverpath").toString());
			} else if (browser.equalsIgnoreCase("chrome")) {
				return getChromeDriver(seleniumconfig.get("chromedriverpath").toString());
			} else if ((browser.equalsIgnoreCase("ie")) || (browser.equalsIgnoreCase("internetexplorer"))
					|| (browser.equalsIgnoreCase("internet explorer"))) {
				return getInternetExplorerDriver(seleniumconfig.get("iedriverpath").toString());
			}
		}
		return getFirefoxDriver(seleniumconfig.get("gekodriverpath").toString());
	}

	//initiate chrome
	private static WebDriver getChromeDriver(String driverpath) {
		System.setProperty("webdriver.chrome.driver", driverpath);
		capabilities.setJavascriptEnabled(true);
		return new ChromeDriver(capabilities);
	}
	
	//initiate IE
	private static WebDriver getInternetExplorerDriver(String driverpath) {
		System.setProperty("webdriver.ie.driver", driverpath);
		capabilities.setCapability("ignoreZoomSetting", true);
		return new InternetExplorerDriver(capabilities);
	}

	//initiate firefox
	private static WebDriver getFirefoxDriver(String driverpath) {
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("browser.cache.disk.enable", false);
		System.setProperty("webdriver.gecko.driver", driverpath);
		Reporter.log("Firefox browser is launched");
		return new FirefoxDriver();
	}
}