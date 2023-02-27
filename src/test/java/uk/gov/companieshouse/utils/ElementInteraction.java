package uk.gov.companieshouse.utils;

import static org.openqa.selenium.support.ui.ExpectedConditions.attributeToBeNotEmpty;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
        waitUntilElementDisplayed(element);
        JavascriptExecutor jexec = (JavascriptExecutor) testContext.getWebDriver();
        jexec.executeScript("var select = arguments[0]; for(var i = 0; i < select.options.length; i++)"
                + "{ if(select.options[i].text == arguments[1]){ select.options[i].selected = true; } }", element, value);
    }

    /**
     * Use JavascriptExecutor to get around an issue with Chips, where using the sendKeys
     * method results in errors tryin to type into the hidden text boxes in PSOD.
     * @param element the text box element.
     * @param value the value to select.
     */
    public void typeText(WebElement element, String value) {
        JavascriptExecutor jse = (JavascriptExecutor)testContext.getWebDriver();
        jse.executeScript("arguments[0].value='" + value + "';", element);
    }

    public void doubleClick(WebElement element) {
        Actions actions = new Actions(testContext.getWebDriver());
        actions.doubleClick(element).perform();
    }

    public WebDriverWait getWebDriverWait(int secondsToWait) {
        return new WebDriverWait(testContext.getWebDriver(), Duration.ofSeconds(secondsToWait));
    }

    public void clearField(WebElement webElement) {
        webElement.sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
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
