package uk.gov.companieshouse.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.utils.TestContext;
import uk.gov.companieshouse.utils.XmlHelper;


public class EfTestHarnessPage extends ChipsCommonPage<EfTestHarnessPage> {

    public final TestContext testContext;
    public final XmlHelper xmlHelper;
    public static final Logger log = LoggerFactory.getLogger(EfTestHarnessPage.class);

    /**
     * Required constructor for class.
     */
    public EfTestHarnessPage(TestContext testContext, XmlHelper xmlHelper) {
        super(testContext);
        this.testContext = testContext;
        this.xmlHelper = xmlHelper;
        PageFactory.initElements(testContext.getWebDriver(), this);
    }

    @FindBy(how = How.ID, using = "form1:_id5")
    private WebElement xmlTextField;

    /**
     * Enter the form XML into the text field.
     * @param xml The XML to enter
     */
    public EfTestHarnessPage enterFormXml(String xml) {
        xmlTextField.sendKeys(xml);
        waitElementTextNotEmpty(xmlTextField);
        return this;
    }

}
