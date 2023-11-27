package uk.gov.companieshouse.pageobjects.companysearch;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.pageobjects.GlobalNavBar;
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

    @FindBy(how = How.LINK_TEXT, using = "search")
    private WebElement searchLink;
    @FindBy(how = How.CSS, using = "#header > .pageheader > .titleLabel")
    private WebElement elementSearchTitle;
    @FindBy(how = How.ID, using = "form1:task_companySearchParameters_companyName:field")
    private WebElement elementSearchCompanyName;
    @FindBy(how = How.ID, using = "form1:task_allMatchingCompanies:0:companyRow_companyName__1:output")
    private WebElement elementSearchResultCompanyName;
    @FindBy(how = How.ID, using = "form1:task_allMatchingCompanies:0:task_selectCompanyDetailsFromTable")
    private WebElement elementSearchViewCompany;

    /**
     * Open the company search screen and find the name used in the test.
     */
    public CompanySearchPage findCompanyByNameFromMenu() {
        String companyName = companyDetails.getCompanyObject().getName();
        openMenuCompanySearch();
        enterCompanyName(companyName);
        searchLink.click();
        log.info("Searching for company {}", companyName);
        verifyCompanyName(companyName);
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
        globalNavBar
                .waitUntilDisplayed()
                .clickSubMenuItem("Company...", "Company Search");
        log.info("Opening Company Search from Menu...");
        waitCompanySearch();
        return this;
    }

    private CompanySearchPage waitCompanySearch() {
        waitForSpecificTextInElement(elementSearchTitle, "Company Search");
        return this;
    }

    private CompanySearchPage enterCompanyName(String companyName) {
       elementSearchCompanyName.sendKeys(companyName);
       return this;
    }

    private CompanySearchPage verifyCompanyName(String companyName) {
        waitForSpecificTextInElement(elementSearchResultCompanyName, companyName.toUpperCase());
        log.info("Company name {} found.", companyName);
        return this;
    }

}
