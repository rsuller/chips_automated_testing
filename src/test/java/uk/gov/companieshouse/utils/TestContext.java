package uk.gov.companieshouse.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import uk.gov.companieshouse.testData.User;

import java.util.logging.Logger;

/**
 * Shared context to be passed in tests.
 */
public class TestContext {

    public static final Logger log = Logger.getLogger("log");
    WebDriver driver;
    User user;

    public WebDriver getWebDriver() {
        if (driver == null) {
            System.setProperty("webdriver.ie.driver", "drivers/IEDriverServer.exe");
            InternetExplorerOptions ieOptions = new InternetExplorerOptions();
            ieOptions.attachToEdgeChrome();
            ieOptions.ignoreZoomSettings();
            ieOptions.withEdgeExecutablePath("C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe");

            driver = new InternetExplorerDriver(ieOptions);
        }
        return driver;

    }

    public void setUpUser(User user) {
       this.user = user;
    }

    public User getUser() {
        if (null == user) {
            throw new NullPointerException("You must set up a user to interact with CHIPS.");
        }
        return user;
    }
}
