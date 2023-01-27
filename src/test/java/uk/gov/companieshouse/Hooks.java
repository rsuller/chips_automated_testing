package uk.gov.companieshouse;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import uk.gov.companieshouse.utils.TestContext;

public class Hooks {
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
        testContext.getWebDriver().close();
    }
}
