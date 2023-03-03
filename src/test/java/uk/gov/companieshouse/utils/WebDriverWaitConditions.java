package uk.gov.companieshouse.utils;

import static org.openqa.selenium.support.ui.ExpectedConditions.attributeToBeNotEmpty;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
public class WebDriverWaitConditions {

    public TestContext testContext;

    public WebDriverWaitConditions(TestContext testContext) {
        this.testContext = testContext;
    }

    public WebDriverWait getWebDriverWait(int secondsToWait) {
        return new WebDriverWait(testContext.getWebDriver(), Duration.ofSeconds(secondsToWait));
    }

    public void waitForSpecificTextInElement(WebElement element, String text) {
        try {
            getWebDriverWait(10).until(ExpectedConditions.textToBePresentInElement(element, text));
        } catch (TimeoutException exception) {
            throw new TimeoutException("Expected value: " + text + " not found in element: " + element);
        }
    }

    public void waitElementTextNotEmpty(WebElement element) {
        try {
            getWebDriverWait(10).until(attributeToBeNotEmpty(element, "value"));
        } catch (TimeoutException exception) {
            throw new TimeoutException("The following element was not populated with expected text: " + element);
        }
    }

    public void waitUntilElementDisplayed(WebElement element) {
        try {
            getWebDriverWait(10).until(visibilityOf(element));
        } catch (TimeoutException exception) {
            throw new TimeoutException("The following element was not displayed as expected: " + element);
        }

    }
}
