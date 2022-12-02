package uk.gov.companieshouse.pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.utils.ElementInteraction;
import uk.gov.companieshouse.utils.TestContext;


public class OrgUnitPage extends ElementInteraction {

    public static final Logger log = LoggerFactory.getLogger(OrgUnitPage.class);

    TestContext testContext;
    @FindBy(how = How.ID, using = "form1:orgUnits")
    private WebElement orgUnitSelect;
    @FindBy(how = How.ID, using = "form1:save")
    private WebElement saveLink;
    @FindBy(how = How.CSS, using = "div[class='bannerrow'] > [class='breadleft']")
    private WebElement chipsBanner;

    public OrgUnitPage(TestContext testContext) {
        super(testContext);
        this.testContext = testContext;
        PageFactory.initElements(testContext.getWebDriver(), this);
    }

    public void selectOrgUnit(String orgUnit) {
        log.info("Attempting to select the the org unit {}", orgUnit);

        // Check if user already logged into required org unit
        if (chipsBanner.getText().contains(orgUnit)) {
            log.info("User is already logged in to {}, no need to amend", orgUnit);
        } else {
            selectByText(orgUnitSelect, orgUnit);
        }

        saveLink.click();
    }

}
