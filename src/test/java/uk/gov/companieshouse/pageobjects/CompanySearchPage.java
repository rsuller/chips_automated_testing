package uk.gov.companieshouse.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.testdata.CompanyDetails;
import uk.gov.companieshouse.utils.ElementInteraction;
import uk.gov.companieshouse.utils.TestContext;


public class CompanySearchPage extends ElementInteraction {

    public static final Logger log = LoggerFactory.getLogger(CompanySearchPage.class);

    public final TestContext testContext;
    public final GlobalNavBar globalNavBar;
    public final CompanyDetails companyDetails;

    /**
     * Required constructor for class.
     */
    public CompanySearchPage(TestContext testContext, GlobalNavBar globalNavBar, CompanyDetails companyDetails) {
        super(testContext);
        this.testContext = testContext;
        this.globalNavBar = globalNavBar;
        this.companyDetails = companyDetails;
        PageFactory.initElements(testContext.getWebDriver(), this);
    }

    @FindBy(how = How.ID, using = "form1:task_companySearchParameters_companyNumber:field")
    private WebElement companyNumberInput;
    @FindBy(how = How.LINK_TEXT, using = "search")
    private WebElement searchLink;
    @FindBy(how = How.CSS, using = "#header > .pageheader > .titleLabel")
    private WebElement elementSearchTitle;
    @FindBy(how = How.ID, using = "form1:task_companySearchParameters_companyNumber:field")
    private WebElement elementSearchCompanyNumber;
    @FindBy(how = How.ID, using = "form1:task_allMatchingCompanies:0:companyRow_companyNumber__1:output")
    private WebElement elementSearchResultCompanyNumber;
    @FindBy(how = How.ID, using = "form1:task_allMatchingCompanies:0:task_selectCompanyDetailsFromTable")
    private WebElement elementSearchViewCompany;
    @FindBy(how = How.CSS, using = "a[title='Show next page']")
    private WebElement nextPage;

    /**
     * Open the company search screen and find the number used in the test.
     */
    public CompanySearchPage findCompanyByNumberFromMenu() {
        String companyNumber = companyDetails.getCompanyObject().getNumber();
        openMenuCompanySearch();
        enterCompanyNumber(companyNumber);
        searchLink.click();
        log.info("Searching for company {}", companyNumber);
        verifyCompanyNumber(companyNumber);
        return this;
    }

    /**
     * Open the company details screen by clicking the icon.
     */
    public CompanySearchPage openCompanyDetails() {
        elementSearchViewCompany.click();
        log.info("Opening company details...");
        log.info("Company details screen displayed successfully");
        return this;
    }

    private CompanySearchPage openMenuCompanySearch() {
        globalNavBar.clickSubMenuItem("Company...", "Company Search");
        log.info("Opening Company Search from Menu...");
        waitCompanySearch();
        return this;
    }

    private CompanySearchPage waitCompanySearch() {
        waitForSpecificTextInElement(elementSearchTitle, "Company Search");
        return this;
    }

    private CompanySearchPage enterCompanyNumber(String companyNumber) {
       typeText(elementSearchCompanyNumber, companyNumber);
       return this;
    }

    private CompanySearchPage verifyCompanyNumber(String companyNumber) {
        waitForSpecificTextInElement(elementSearchResultCompanyNumber, companyNumber);
        log.info("Company number {} found.", companyNumber);
        return this;
    }

}
