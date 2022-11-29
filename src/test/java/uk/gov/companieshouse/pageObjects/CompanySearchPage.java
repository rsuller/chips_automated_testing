package uk.gov.companieshouse.pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.w3c.dom.html.HTMLInputElement;
import uk.gov.companieshouse.utils.TestContext;

public class CompanySearchPage {

    TestContext testContext;

    public CompanySearchPage(TestContext testContext) {
        this.testContext = testContext;
        PageFactory.initElements(testContext.getWebDriver(), this);
    }

    @FindBy(how = How.ID, using = "form1:task_companySearchParameters_companyNumber:field")
    private WebElement companyNumberInput;

    @FindBy(how = How.LINK_TEXT, using = "search")
    private WebElement searchLink;


    public void searchForCompanyNumber(String companyNumber) {
        companyNumberInput.sendKeys(companyNumber);
        searchLink.click();
    }
}
