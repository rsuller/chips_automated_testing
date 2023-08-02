package uk.gov.companieshouse.utils;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.testdata.User;

/**
 * Shared context to be passed in tests.
 */
public class TestContext {

    public static final Logger log = LoggerFactory.getLogger(TestContext.class);
    User user;

    public Env getEnv() {
        return new Env();
    }

    /**
     * Initialise webdriver for Edge in IE mode.
     */
    public WebDriver getWebDriver() {
        return WebDriverManager.getDriver();
    }

    public WebDriver quitWebDriver() {
        return WebDriverManager.quitDriver();
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
