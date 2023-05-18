package uk.gov.companieshouse;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import uk.gov.companieshouse.pageobjects.GlobalNavBar;
import uk.gov.companieshouse.pageobjects.Logout;
import uk.gov.companieshouse.utils.TestContext;

public class Hooks {

    public final TestContext testContext;
    public final GlobalNavBar globalNavBar;
    public final Logout logout;

    /**
     * Required constructor for class.
     */
    public Hooks(TestContext testContext, GlobalNavBar globalNavBar, Logout logout)
    {
        this.testContext = testContext;
        this.globalNavBar = globalNavBar;
        this.logout = logout;
    }

    @Before
    public void setUp() {
        testContext.getWebDriver().get(
                testContext.getEnv().config.getString("chips_url"));
    }

    /**
     * Logout by trying to close open batches.
     */
    @After
    public void tearDown() {
        globalNavBar.clickLogoutLabel();
        testContext.getWebDriver().switchTo().alert().accept();
        logout.clickIgnoreOpenBatches();
        testContext.getWebDriver().close();
    }
}
