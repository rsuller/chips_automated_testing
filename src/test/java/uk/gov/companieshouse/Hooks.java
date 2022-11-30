package uk.gov.companieshouse;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import uk.gov.companieshouse.utils.ConfigReader;
import uk.gov.companieshouse.utils.TestContext;

public class Hooks {
    public TestContext testContext;
    private final ConfigReader configReader;

    public Hooks(TestContext testContext,
                 ConfigReader configReader) {
        this.testContext = testContext;
        this.configReader = configReader;
    }

    @Before
    public void setUp() {
        testContext.getWebDriver().get(
                configReader.getConfigProperty("chips_url"));
    }

    @After
    public void tearDown() {
        testContext.getWebDriver().quit();
    }
}
