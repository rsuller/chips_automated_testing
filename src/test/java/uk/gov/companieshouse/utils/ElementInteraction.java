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
