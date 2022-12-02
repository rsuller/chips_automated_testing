package uk.gov.companieshouse.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.testData.User;

/**
 * Shared context to be passed in tests.
 */
public class TestContext {

    public static final Logger log = LoggerFactory.getLogger(TestContext.class);
    WebDriver driver;
    User user;

    public WebDriver getWebDriver() {
        if (driver == null) {
            /*
            Set up for Edge in IE Mode
             */
//            System.setProperty("webdriver.ie.driver", "drivers/IEDriverServer.exe");
//            InternetExplorerOptions ieOptions = new InternetExplorerOptions();
//            ieOptions.attachToEdgeChrome();
//            ieOptions.ignoreZoomSettings();
//            ieOptions.withEdgeExecutablePath("C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe");
//
//            driver = new InternetExplorerDriver(ieOptions);

            /*
            Set up for Edge only
             */
            System.setProperty("webdriver.edge.driver", "drivers/msedgedriver.exe");
            driver = new EdgeDriver();
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
