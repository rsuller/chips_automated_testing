package uk.gov.companieshouse.pageobjects.pscpages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import uk.gov.companieshouse.testdata.datamodel.PersonOfSignificantControl;
import uk.gov.companieshouse.utils.DateFormat;
import uk.gov.companieshouse.utils.TestContext;

public class PscRegisterDateTab {
    TestContext testContext;
    @FindBy(how = How.ID, using = "form1:registerEntryDateTabSubView:doc_filingDetails_registerEntryDate:field")
    private WebElement registerDateElement;
    @FindBy(how = How.LINK_TEXT, using = "Register Date")
    private WebElement registerDateTabLink;
    @FindBy(how = How.LINK_TEXT, using = "save")
    private WebElement saveLink;

    /**
     * Required constructor for class.
     *
     * @param testContext required for shared state.
     */
    public PscRegisterDateTab(TestContext testContext) {
        this.testContext = testContext;
        PageFactory.initElements(testContext.getWebDriver(), this);
    }

    public void enterPscRegisterDate(PersonOfSignificantControl psc) {
        registerDateTabLink.click();
        registerDateElement.sendKeys(DateFormat.getDateAsString(psc.getDateOfNotification()));
        saveLink.click();
    }
}
