package uk.gov.companieshouse.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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
    private final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();
    WebDriver driver;
    User user;

    public Env getEnv() {
        return new Env();
    }

    /**
     * Initialise webdriver for Edge in IE mode.
     */
    public WebDriver getWebDriver() {
        if (threadLocalDriver.get() == null) {
            /*
            Set up for Edge in IE Mode
             */
            log.info("Setting up Edge in IE Mode for testing");

            System.setProperty("webdriver.ie.driver", "drivers/IEDriverServer.exe");
            System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
            InternetExplorerOptions ieOptions = new InternetExplorerOptions();
            ieOptions.attachToEdgeChrome();
            ieOptions.ignoreZoomSettings();
            ieOptions.withEdgeExecutablePath("C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe");
            ieOptions.setCapability(
                    InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);

            driver = new ChromeDriver();
            threadLocalDriver.set(driver);

        }
        return threadLocalDriver.get();

    }

    /**
     * Closes the webdriver.
     */
    public void closeWebDriver() {
        if (threadLocalDriver.get() != null) {
            threadLocalDriver.get().quit();
            threadLocalDriver.remove(); // Removes the WebDriver instance from the current thread
        }
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
