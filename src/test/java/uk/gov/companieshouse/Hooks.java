package uk.gov.companieshouse;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.utils.TestContext;

public class Hooks {

    public static final Logger log = LoggerFactory.getLogger(Hooks.class);
    public TestContext testContext;

    public Hooks(TestContext testContext) {
        this.testContext = testContext;
    }

    @Before
    public void setUp() {
        testContext.getWebDriver().get(
                testContext.getEnv().config.getString("chips_url"));
    }

    @After
    public void tearDown() {
        testContext.getWebDriver().quit();
        testContext.getWebDriver().close();
    }
}
