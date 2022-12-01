package uk.gov.companieshouse.pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.gov.companieshouse.utils.ElementInteraction;
import uk.gov.companieshouse.utils.TestContext;

import java.time.Duration;
import java.util.logging.Logger;

public class OrgUnitPage extends ElementInteraction {

    public static final Logger log = Logger.getLogger("log");

    TestContext testContext;
    @FindBy(how = How.ID, using = "form1:orgUnits")
    private WebElement orgUnitSelect;
    @FindBy(how = How.LINK_TEXT, using = "Save")
    private WebElement saveLink;
    @FindBy(how = How.CSS, using = "div[class='bannerrow'] > [class='breadleft']")
    private WebElement chipsBanner;

    public OrgUnitPage(TestContext testContext) {
        this.testContext = testContext;
    }

    public void selectOrgUnit(String orgUnit) {
        // Check if user already signed in to org unit
        try {
            new WebDriverWait(testContext.getWebDriver(), Duration.ofSeconds(10))
                    .until(ExpectedConditions.textToBePresentInElement(chipsBanner, orgUnit));
            log.info("The org unit: " + orgUnit + " is currently selected by the user");
        } catch (NullPointerException exception) {
            throw new NullPointerException("The org unit " + orgUnit + " is not present. Check your UAM settings.");
        }
        // Check if the option is present in the list
        try {
            selectByValue(orgUnitSelect, orgUnit);
        } catch (NullPointerException exception) {
            throw new NullPointerException("The org unit " + orgUnit + " is not present. Check your UAM settings.");
        }

        saveLink.click();
    }

}
