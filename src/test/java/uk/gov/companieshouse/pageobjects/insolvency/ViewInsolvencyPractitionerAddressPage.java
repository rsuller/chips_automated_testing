package uk.gov.companieshouse.pageobjects.insolvency;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import uk.gov.companieshouse.pageobjects.ChipsCommonPage;
import uk.gov.companieshouse.utils.TestContext;

public class ViewInsolvencyPractitionerAddressPage
        extends ChipsCommonPage<ViewInsolvencyPractitionerAddressPage> {

    @FindBy(how = How.ID, using = "form1:task_selectIP")
    private WebElement elementSelectIpLink;

    /**
     * Required constructor for class.
     */
    public ViewInsolvencyPractitionerAddressPage(TestContext testContext) {
        super(testContext);
    }

    /**
     * Wait for the select IP link to be displayed and click it.
     */
    public ViewInsolvencyPractitionerAddressPage clickSelectIpLink() {
        waitUntilElementDisplayed(elementSelectIpLink);
        elementSelectIpLink.click();
        return this;
    }
}
