package uk.gov.companieshouse.pageobjects.restoration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.pageobjects.ChipsCommonPage;
import uk.gov.companieshouse.utils.TestContext;


public class Rt01Page extends ChipsCommonPage<Rt01Page> {

    public final TestContext testContext;
    public static final Logger log = LoggerFactory.getLogger(Rt01Page.class);

    /**
     * Required constructor for class.
     */
    public Rt01Page(TestContext testContext) {
        super(testContext);
        this.testContext = testContext;
        PageFactory.initElements(testContext.getWebDriver(), this);
    }


    @FindBy(how = How.ID, using = "form1:doc_corporateBody_restoration_presenterLetterReference:field")
    private WebElement elementYourReference;

    /**
     * Enter the reference number.
     */
    public Rt01Page enterYourReference() {
        waitUntilElementDisplayed(elementYourReference);
        log.info("Entering reference number...");
        elementYourReference.sendKeys("12345");
        return this;
    }

}

