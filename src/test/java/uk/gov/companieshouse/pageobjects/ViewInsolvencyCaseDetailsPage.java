package uk.gov.companieshouse.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import uk.gov.companieshouse.utils.TestContext;


public class ViewInsolvencyCaseDetailsPage extends ChipsCommonPage<ViewInsolvencyCaseDetailsPage> {

    /**
     * Required constructor for class.
     */
    public ViewInsolvencyCaseDetailsPage(TestContext testContext) {
        super(testContext);
        this.testContext = testContext;
        PageFactory.initElements(testContext.getWebDriver(), this);
    }

    @FindBy(how = How.CSS, using = "div[class='subheader'] > span[class='subtitle']")
    private WebElement elementPageSubTitle;

    public void waitUntilCaseDetailsPageDisplayed() {
        waitForSpecificTextInElement(elementPageSubTitle, "View Insolvency Case Details");
    }

}
