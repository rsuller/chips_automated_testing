package uk.gov.companieshouse.pageobjects;

import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.testdata.DocumentDetails;
import uk.gov.companieshouse.utils.TestContext;


public class InsolvencyPractitionerSearchScreen extends ChipsCommonPage<InsolvencyPractitionerSearchScreen> {

    public final TestContext testContext;
    public final DocumentDetails documentDetails;
    public static final Logger log = LoggerFactory.getLogger(InsolvencyPractitionerSearchScreen.class);

    /**
     * Required constructor for class.
     */
    public InsolvencyPractitionerSearchScreen(TestContext testContext, DocumentDetails documentDetails) {
        super(testContext);
        this.testContext = testContext;
        this.documentDetails = documentDetails;
        PageFactory.initElements(testContext.getWebDriver(), this);
    }

    @FindBy(how = How.ID, using = "form1:task_ipType:field2")
    private WebElement elementIpNumberRadioButton;
    @FindBy(how = How.ID, using = "form1:task_ipNumber:field")
    private WebElement elementIpNumberField;
    @FindBy(how = How.ID, using = "form1:task_search")
    private WebElement elementSearchLink;
    @FindBy(how = How.CSS, using = "table[id='form1:task_dataModel'] tbody tr")
    private List<WebElement> elementSearchResultRows;
    @FindBy(how = How.ID, using = "form1:allocateIP:task_allocateIP")
    private WebElement elementAllocateIp;
    @FindBy(how = How.ID, using = "form1:task_insolvencyPractitioner_ipNumber:output")
    private WebElement elementIpNumber;

    /**
     * search and select IP.
     */
    public InsolvencyPractitionerSearchScreen searchAndSelectIp() {
        waitUntilElementDisplayed(elementAllocateIp);
        clickAllocateIpLink();
        clickIpNumberRadioButton();
        typeIpNumber();
        clickSearchLink();
        documentDetails.setAllocatedIpNumber(getSelectedIpNumber());
        return this;
    }

    private InsolvencyPractitionerSearchScreen clickSearchLink() {
        elementSearchLink.click();
        return this;
    }

    public InsolvencyPractitionerSearchScreen clickIpNumberRadioButton() {
        elementIpNumberRadioButton.click();
        return this;
    }

    private InsolvencyPractitionerSearchScreen typeIpNumber() {
        waitUntilElementDisplayed(elementIpNumberField);
        elementIpNumberField.sendKeys("00009401");
        return this;
    }

    private InsolvencyPractitionerSearchScreen clickAllocateIpLink() {
        elementAllocateIp.click();
        return this;
    }

    private String getSelectedIpNumber() {
        return elementIpNumber.getText();
    }

}

