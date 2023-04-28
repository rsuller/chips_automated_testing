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
     * There is a known issue with Selenium 4 where after using the necessary select method above, the screen
     * does not refresh and hidden company name/number fields are not displayed. The following methods moving
     * the cursor up and down results in the correct form being selected and required fields are displayed.
     *
     * @param element - the select web element
     * @param value the value to be selected
     * @param javascriptIssue true if affected by known javascript issue
     */
    public void selectByText(WebElement element, String value, boolean javascriptIssue) {
        if (javascriptIssue) {
            selectByText(element, value);
            element.sendKeys(Keys.UP);
            element.sendKeys(Keys.DOWN);
        }
    }


    /**
     * Use JavascriptExecutor to get around an issue with Chips, where using the sendKeys
     * method results in errors trying to type into the hidden text boxes in PSOD.
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


    public void clearField(WebElement webElement) {
        webElement.sendKeys(Keys.CONTROL,"a");
        webElement.sendKeys(Keys.DELETE);
    }

}
