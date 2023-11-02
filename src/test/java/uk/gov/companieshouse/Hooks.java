package uk.gov.companieshouse;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.pageobjects.GlobalNavBar;
import uk.gov.companieshouse.pageobjects.Logout;
import uk.gov.companieshouse.testdata.DocumentDetails;
import uk.gov.companieshouse.utils.TestContext;


public class Hooks {

    public final TestContext testContext;
    public final GlobalNavBar globalNavBar;
    public final Logout logout;
    public final DocumentDetails documentDetails;

    public static final Logger log = LoggerFactory.getLogger(Hooks.class);

    /**
     * Required constructor for class.
     */
    public Hooks(TestContext testContext, GlobalNavBar globalNavBar, Logout logout, DocumentDetails documentDetails) {
        this.testContext = testContext;
        this.globalNavBar = globalNavBar;
        this.logout = logout;
        this.documentDetails = documentDetails;
    }


    /**
     * Set up the webdriver.
     */
    @Before
    public void setUp() {
        testContext.getWebDriver().manage().deleteAllCookies();
        testContext.getWebDriver().get(
                testContext.getEnv().config.getString("chips_url"));
        testContext.getWebDriver().manage().window().maximize();
    }

    @Before("@electronic")
    public void setElectronicFilingMethod() {
        documentDetails.setFilingMethod("electronic filing");
        log.info("Testing an electronic filing");
    }

    @Before("@fes_scanned")
    public void setFesScannedFilingMethod() {
        documentDetails.setFilingMethod("front-end scanned");
        log.info("Testing a FES filing");
    }

    @Before("@paper")
    public void setPaperFilingMethod() {
        documentDetails.setFilingMethod("paper filing");
        log.info("Testing a paper filing");
    }

    /**
     * Logout by trying to close open batches.
     */
    @After
    public void tearDown() {
      testContext.getWebDriver().quit();
    }
}
