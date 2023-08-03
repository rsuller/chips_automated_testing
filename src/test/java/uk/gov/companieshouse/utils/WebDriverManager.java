package uk.gov.companieshouse.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebDriverManager {
    public static final Logger log = LoggerFactory.getLogger(WebDriverManager.class);
    private static final ThreadLocal<WebDriver> webDriverThreadLocal = new ThreadLocal<>();
    public static WebDriver driver = null;

    public static WebDriver getDriver() {
        return webDriverThreadLocal.get();
    }

    /**
     * Initialise webdriver for Edge in IE mode.
     */
    public static void setDriver() {
        log.info("Setting up Edge in IE Mode for testing");

        System.setProperty("webdriver.ie.driver", "drivers/IEDriverServer.exe");
        InternetExplorerOptions ieOptions = new InternetExplorerOptions();
        ieOptions.attachToEdgeChrome();
        ieOptions.ignoreZoomSettings();
        ieOptions.withEdgeExecutablePath("C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe");

        webDriverThreadLocal.set(new InternetExplorerDriver(ieOptions));
    }

    /**
     * Quit the webdriver.
     */
    public static WebDriver quitDriver() {
        driver = webDriverThreadLocal.get();
        if (driver != null) {
            driver.close();
            webDriverThreadLocal.remove();
        }
        return driver;
    }
}
