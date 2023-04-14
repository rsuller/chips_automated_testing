package uk.gov.companieshouse.pageobjects.pscpages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import uk.gov.companieshouse.pageobjects.ChipsCommonPage;
import uk.gov.companieshouse.testdata.datamodel.PersonOfSignificantControl;
import uk.gov.companieshouse.utils.TestContext;

public class PscNatureOfControlTab extends ChipsCommonPage<PscNatureOfControlTab> {

    TestContext testContext;
    @FindBy(how = How.ID, using =
            "form1:natureOfControlTabSubView:task_natureOfControlTab_natureOfControlCategoryType:field")
    private WebElement natureOfControlCategoryField;
    @FindBy(how = How.ID, using = "form1:natureOfControlTabSubView:task_natureOfControlTab_natureOfControlType:field")
    private WebElement natureOfControlField;
    @FindBy(how = How.LINK_TEXT, using = "Nature of Control")
    private WebElement natureOfControlLink;
    @FindBy(how = How.LINK_TEXT, using = "add")
    private WebElement addLink;

    /**
     * Required constructor for class.
     *
     * @param testContext required for shared state.
     */
    public PscNatureOfControlTab(TestContext testContext) {
        super(testContext);
        this.testContext = testContext;
        PageFactory.initElements(testContext.getWebDriver(), this);
    }

    public void enterPscNatureOfControlDetails(PersonOfSignificantControl psc) {
        natureOfControlLink.click();
        selectNatureOfControlCategory(psc.getNatureOfControl().getKey());
        selectNatureOfControl(psc.getNatureOfControl().getValue());
        addDetails();
    }

    private void addDetails() {
        addLink.click();
    }

    private void selectNatureOfControl(String natureOfControl) {
        selectByText(natureOfControlField, natureOfControl);
    }

    private void selectNatureOfControlCategory(String natureOfControlCategory) {
        selectByText(natureOfControlCategoryField, natureOfControlCategory);
    }
}
