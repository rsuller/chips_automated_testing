package uk.gov.companieshouse.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class ElementInteraction {

    private final TestContext testContext;

    public ElementInteraction(TestContext testContext) {
        this.testContext = testContext;
    }

    /**
     * Use JavascriptExecutor to get around a known issue with Selenium 4, where using the Select object
     * results in "unexpected Javascript error" when trying to select from a dropdown.
     * @param element the dropdown element.
     * @param value the value to select.
     */
    public void selectByText(WebElement element, String value) {
        getWebDriverWait(10).until(visibilityOf(element));
        JavascriptExecutor jexec = (JavascriptExecutor) testContext.getWebDriver();
        jexec.executeScript("var select = arguments[0]; for(var i = 0; i < select.options.length; i++)"
                + "{ if(select.options[i].text == arguments[1]){ select.options[i].selected = true; } }", element, value);
    }

    public WebDriverWait getWebDriverWait(int secondsToWait) {
        return new WebDriverWait(testContext.getWebDriver(), Duration.ofSeconds(secondsToWait));
    }
}
