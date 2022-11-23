package uk.gov.companieshouse;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import uk.gov.companieshouse.utils.TestContext;

public class Hooks {
    public TestContext testContext;

    public Hooks(TestContext testContext) {
        this.testContext = testContext;
    }

    @Before
    public void setUp() {
        testContext.getWebDriver().get("http://www.bing.com");
    }

    @After
    public void tearDown() {
        testContext.getWebDriver().quit();
    }
}
