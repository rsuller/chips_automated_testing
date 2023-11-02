package uk.gov.companieshouse.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.testdata.User;

/**
 * Shared context to be passed in tests.
 */
public class TestContext {

    public static final Logger log = LoggerFactory.getLogger(TestContext.class);
    WebDriver driver;
    User user;

    public Env getEnv() {
        return new Env();
    }

    /**
     * Initialise webdriver for Edge in IE mode.
     */
    public WebDriver getWebDriver() {
        if (driver == null) {
            /*
            Set up for Edge in IE Mode
             */
            log.info("Setting up Edge in IE Mode for testing");

            System.setProperty("webdriver.ie.driver", "drivers/IEDriverServer.exe");
            InternetExplorerOptions ieOptions = new InternetExplorerOptions();
            ieOptions.attachToEdgeChrome();
            ieOptions.ignoreZoomSettings();
            ieOptions.withEdgeExecutablePath("C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe");
            ieOptions.setCapability(
                    InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);

            driver = new InternetExplorerDriver(ieOptions);

            /*
            Set up for Edge only
            //TODO Keep this just in case we need to compare with Edge behaviour
             */
            //log.info("Setting up Edge Browser");
            //System.setProperty("webdriver.edge.driver", "drivers/msedgedriver.exe");
            //driver = new EdgeDriver();
        }
        return driver;

    }

    public void setUpUser(User user) {
        this.user = user;
    }

    /**
     * Get user details to enable login.
     */
    public User getUser() {
        if (null == user) {
            throw new NullPointerException("You must set up a user to interact with CHIPS.");
        }
        return user;
    }

}
