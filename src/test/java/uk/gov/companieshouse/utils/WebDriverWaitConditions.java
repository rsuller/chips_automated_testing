package uk.gov.companieshouse.utils;

import static org.openqa.selenium.support.ui.ExpectedConditions.attributeToBeNotEmpty;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

import java.time.Duration;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverWaitConditions {

    public TestContext testContext;

    public WebDriverWaitConditions(TestContext testContext) {
        this.testContext = testContext;
    }

    public WebDriverWait getWebDriverWait(int secondsToWait) {
        return new WebDriverWait(testContext.getWebDriver(), Duration.ofSeconds(secondsToWait));
    }

    /**
     * Common method to wait for a specific text within an element.
     * @param element the element
     * @param text the text to expect in the element.
     */
    public void waitForSpecificTextInElement(WebElement element, String text) {
        try {
            getWebDriverWait(10).until(ExpectedConditions.textToBePresentInElement(element, text));
        } catch (TimeoutException exception) {
            throw new TimeoutException("Expected value: " + text + " not found in element: " + element);
        }
    }

    /**
     * Common method to wait for an element's value not to be empty.
     * @param element the element whose value is to be checked.
     */
    public void waitElementTextNotEmpty(WebElement element) {
        try {
            getWebDriverWait(10).until(attributeToBeNotEmpty(element, "value"));
        } catch (TimeoutException exception) {
            throw new TimeoutException("The following element was not populated with expected text: " + element);
        }
    }

    /**
     * Common method to wait for an elementto be displayed.
     * @param element the element to be checked.
     */
    public void waitUntilElementDisplayed(WebElement element) {
        try {
            getWebDriverWait(10).until(visibilityOf(element));
        } catch (TimeoutException exception) {
            throw new TimeoutException("The following element was not displayed as expected: " + element);
        }
    }

}
