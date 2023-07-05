package uk.gov.companieshouse.pageobjects.accounts;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.enums.Form;
import uk.gov.companieshouse.pageobjects.ChipsCommonPage;
import uk.gov.companieshouse.testdata.DocumentDetails;
import uk.gov.companieshouse.utils.TestContext;

public class AnnualAccountsPage extends ChipsCommonPage<AnnualAccountsPage> {

    public final TestContext testContext;
    public final DocumentDetails documentDetails;
    public static final Logger log = LoggerFactory.getLogger(AnnualAccountsPage.class);

    /**
     * Required constructor for class.
     */
    public AnnualAccountsPage(TestContext testContext, DocumentDetails documentDetails) {
        super(testContext);
        this.testContext = testContext;
        this.documentDetails = documentDetails;
        PageFactory.initElements(testContext.getWebDriver(), this);
    }

    @FindBy(how = How.ID, using = "form1:corporateBody:corporateBody_formattedNextAccountsDueDate:output")
    private WebElement elementNextAaMudDue;
    @FindBy(how = How.ID, using = "form1:doc_corporateBody_accounts_madeUpDate__2:field")
    private WebElement elementAccountsMud;
    @FindBy(how = How.ID, using = "form1:doc_corporateBody_accounts_accountsType__1:field")
    private WebElement elementAccountsTypeSelect;
    @FindBy(how = How.ID, using = "form1:editableTable:0")
    private WebElement elementLastAccountsFiled;

    public String readNextAaMudDueFromScreen() {
        waitUntilFormDisplayed(Form.ACCOUNTS);
        return elementNextAaMudDue.getText();
    }

    /**
     * Enter the next accounts made up date in the text field and store the date in memory for later use.
     * @param nextAccountsMud the date to enter and store.
     */
    public AnnualAccountsPage enterAccountsMud(String nextAccountsMud) {
        documentDetails.setAccountsMud(nextAccountsMud);
        elementAccountsMud.sendKeys(nextAccountsMud);
        return this;
    }

    /**
     * Select the relevant accounts type from the drop-down and store in memory for later use.
     * @param accountsType the accounts type to select as specified in the feature file.
     */
    public AnnualAccountsPage selectAccountsType(String accountsType) {
        documentDetails.setAccountsType(accountsType);
        selectByText(elementAccountsTypeSelect, accountsType);
        return this;
    }


}

