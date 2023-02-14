package uk.gov.companieshouse.pageobjects;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import uk.gov.companieshouse.utils.ElementInteraction;
import uk.gov.companieshouse.utils.TestContext;

public class ChipsCommonPage extends ElementInteraction {

    private static final String URA_BARCODE = "7777788888";
    TestContext testContext;

    public ChipsCommonPage(TestContext testContext) {
        super(testContext);
        this.testContext = testContext;
        PageFactory.initElements(testContext.getWebDriver(),this);
    }

    @FindBy(how = How.ID, using = "form1:task_tabBookTabHeader:2")
    private WebElement residentialAddressLink;

    @FindBy(how = How.ID, using = "form1:residentialAddressTabSubView:officer_residentialAddress_uraBarcode:field")
    private WebElement uraBarcodeInputField;


    public void enterUraBarcode() {
        residentialAddressLink.click();
        uraBarcodeInputField.sendKeys(URA_BARCODE);
    }

    public void clearField(WebElement webElement) {
        webElement.sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
    }
}
