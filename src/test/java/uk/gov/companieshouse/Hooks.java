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
    public Hooks(TestContext testContext, GlobalNavBar globalNavBar, Logout logout, DocumentDetails documentDetails)
    {
        this.testContext = testContext;
        this.globalNavBar = globalNavBar;
        this.logout = logout;
        this.documentDetails = documentDetails;
    }

    @Before
    public void setUp() {
        testContext.getWebDriver().get(
                testContext.getEnv().config.getString("chips_url"));
    }

    @Before("not @fes_scanned")
    public void setNotFesScanned() {
        documentDetails.setFesScanned(false);
        log.info("Not a FES document");
    }

    @Before("@fes_scanned")
    public void setFesScanned() {
        documentDetails.setFesScanned(true);
        log.info("FES document");
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
