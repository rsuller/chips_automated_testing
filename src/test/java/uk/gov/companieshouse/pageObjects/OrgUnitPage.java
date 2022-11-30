package uk.gov.companieshouse.pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import uk.gov.companieshouse.utils.ElementInteraction;
import uk.gov.companieshouse.utils.TestContext;

public class OrgUnitPage extends ElementInteraction {

    TestContext testContext;
    @FindBy(how =  How.ID, using = "form1:orgUnits")
    private WebElement orgUnitSelect;
    @FindBy(how = How.LINK_TEXT, using = "Save")
    private WebElement saveLink;

    public OrgUnitPage(TestContext testContext) {
        this.testContext = testContext;
    }

    public void selectOrgUnit(String orgUnit) {
        try {
            selectByValue(orgUnitSelect, orgUnit);
        } catch (NullPointerException exception) {
            throw new NullPointerException("The org unit " + orgUnit + " is not present. Check your UAM settings.");
        }
        saveLink.click();
    }
}
