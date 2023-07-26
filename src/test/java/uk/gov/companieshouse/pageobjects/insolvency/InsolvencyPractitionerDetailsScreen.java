package uk.gov.companieshouse.pageobjects.insolvency;

import static uk.gov.companieshouse.utils.DateFormat.getDateAsString;

import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.pageobjects.ChipsCommonPage;
import uk.gov.companieshouse.testdata.DocumentDetails;
import uk.gov.companieshouse.utils.TestContext;


public class InsolvencyPractitionerDetailsScreen extends ChipsCommonPage<InsolvencyPractitionerDetailsScreen> {

    public final TestContext testContext;
    public final DocumentDetails documentDetails;
    public static final Logger log = LoggerFactory.getLogger(InsolvencyPractitionerDetailsScreen.class);

    /**
     * Required constructor for class.
     */
    public InsolvencyPractitionerDetailsScreen(TestContext testContext, DocumentDetails documentDetails) {
        super(testContext);
        this.testContext = testContext;
        this.documentDetails = documentDetails;
        PageFactory.initElements(testContext.getWebDriver(), this);
    }

    private static final int ADDRESS_STREET_COLUMN_INDEX = 1;

    @FindBy(how = How.CSS, using = "table[id='form1:task_insolvencyPractitioner_addresses'] tbody tr")
    private List<WebElement> elementListOfIpAddressRows;
    @FindBy(how = How.ID, using = "form1:allocateIP:task_dataModel:0")
    private WebElement elementFirstIp;
    @FindBy(how = How.ID, using = "form1:task_ipEndDate:field")
    private WebElement elementIpEndDate;

    /**
     * click first complete address row.
     */
    public InsolvencyPractitionerDetailsScreen clickFirstCompleteAddressRow() {
        for (WebElement webElement : elementListOfIpAddressRows) {
            List<WebElement> columns = webElement.findElements(By.tagName("td"));
            String columnValue = columns.get(ADDRESS_STREET_COLUMN_INDEX).getAttribute("innerText");

            if (!columnValue.isEmpty()) {
                doubleClick(webElement);
                log.info("Clicking first complete address row");
                break;
            }
        }
        return this;
    }

    /**
     * Select the first IP from the list.
     */
    public InsolvencyPractitionerDetailsScreen clickFirstIp() {
        log.info("Selecting first IP");
        elementFirstIp.click();
        return this;
    }

    public InsolvencyPractitionerDetailsScreen enterIpCeaseDate() {
        typeText(elementIpEndDate, getDateAsString(new Date()));
        return this;
    }

}

