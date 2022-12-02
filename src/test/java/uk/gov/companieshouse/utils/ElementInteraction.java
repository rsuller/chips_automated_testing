package uk.gov.companieshouse.utils;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class ElementInteraction {

    private final TestContext testContext;

    public ElementInteraction(TestContext testContext) {
        this.testContext = testContext;
    }

    public void selectByText(WebElement element, String text) {
        getWebDriverWait(10).until(visibilityOf(element));
        Select select = new Select(element);
        select.selectByVisibleText(text);
    }

    public WebDriverWait getWebDriverWait(int secondsToWait) {
        return new WebDriverWait(testContext.getWebDriver(), Duration.ofSeconds(secondsToWait));
    }
}
