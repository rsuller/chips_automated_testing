package uk.gov.companieshouse.pageobjects.pscpages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import uk.gov.companieshouse.testdata.datamodel.PersonOfSignificantControl;
import uk.gov.companieshouse.utils.TestContext;

public class PscUraTab extends PscAddressDetails {

    TestContext testContext;
    @FindBy(how = How.ID, using = "form1:residentialAddressTabSubView:psc_birthDate:field")
    private WebElement dateOfBirthField;

    public PscUraTab(TestContext testContext) {
        super(testContext);
        this.testContext = testContext;
        PageFactory.initElements(testContext.getWebDriver(), this);
    }

    public void enterUraDetails(PersonOfSignificantControl psc) {
        enterDateOfBirth(psc.getDateOfBirth());
        enterAddress(psc.getAddress());
    }

    private void enterDateOfBirth(String dateOfBirth) {
        dateOfBirthField.sendKeys(dateOfBirth);
    }
}
