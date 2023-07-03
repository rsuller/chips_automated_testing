package uk.gov.companieshouse.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class ElementInteraction extends WebDriverWaitConditions {

    private final TestContext testContext;

    public ElementInteraction(TestContext testContext) {
        super(testContext);
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
     * method results in errors trying to type into the hidden text boxes in PSOD.
     * @param element the text box element.
     * @param value the value to select.
     */
    public void typeText(WebElement element, String value) {
        String js = "arguments[0].setAttribute('value','" + value + "')";
        ((JavascriptExecutor) testContext.getWebDriver()).executeScript(js, element);
        //((JavascriptExecutor) testContext.getWebDriver()).executeScript("return document.readyState").equals("complete");
        ((JavascriptExecutor) testContext.getWebDriver()).executeScript("arguments[0].click();", element);
    }

    public void doubleClick(WebElement element) {
        Actions actions = new Actions(testContext.getWebDriver());
        actions.doubleClick(element).perform();
    }


    public void clearField(WebElement webElement) {
        webElement.sendKeys(Keys.CONTROL,"a");
        webElement.sendKeys(Keys.DELETE);
    }

    public void acceptAlert() {
        testContext.getWebDriver().switchTo().alert().accept();
    }

}
