package uk.gov.companieshouse.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import uk.gov.companieshouse.utils.TestContext;

public class ConfirmCompanyIdentityPopup extends ChipsCommonPage<ConfirmCompanyIdentityPopup> {

    @FindBy(how = How.ID, using = "form1:confirmationPopUp:task_confirmYes")
    private WebElement elementYesLink;

    /**
     * Wait for the confirm company identity popup to be displayed and click the yes link.
     */
    public ConfirmCompanyIdentityPopup clickYesLink() {
        waitUntilElementDisplayed(elementYesLink);
        elementYesLink.click();
        return this;
    }

    /**
     * Required constructor for class.
     */
    public ConfirmCompanyIdentityPopup(TestContext testContext) {
        super(testContext);
    }
}
