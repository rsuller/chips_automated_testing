package uk.gov.companieshouse.pageobjects;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.testdata.DocumentDetails;
import uk.gov.companieshouse.utils.TestContext;


public class InsolvencyPractitionerDetailsScreen extends ChipsCommonPage<InsolvencyPractitionerDetailsScreen> {

    public TestContext testContext;
    public DocumentDetails documentDetails;
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

    /**
     * click first complete address row.
     */
    public InsolvencyPractitionerDetailsScreen clickFirstCompleteAddressRow() {
        for (WebElement webElement : elementListOfIpAddressRows) {
            List<WebElement> columns = webElement.findElements(By.tagName("td"));
            String columnValue = columns.get(ADDRESS_STREET_COLUMN_INDEX).getAttribute("innerText");

            if (!columnValue.isEmpty()) {
                doubleClick(webElement);
                break;
            }
        }
        return this;
    }

}

