package uk.gov.companieshouse.pageobjects.pscpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import uk.gov.companieshouse.pageobjects.ChipsCommonPage;
import uk.gov.companieshouse.testdata.datamodel.PersonOfSignificantControl;
import uk.gov.companieshouse.utils.TestContext;

import java.util.List;

public class PscNatureOfControlTab extends ChipsCommonPage<PscNatureOfControlTab> {

    TestContext testContext;
    @FindBy(how = How.ID, using =
            "form1:natureOfControlTabSubView:task_natureOfControlTab_natureOfControlCategoryType:field")
    private WebElement natureOfControlCategoryField;
    @FindBy(how = How.ID, using = "form1:natureOfControlTabSubView:task_natureOfControlTab_natureOfControlType:field")
    private WebElement natureOfControlField;
    @FindBy(how = How.LINK_TEXT, using = "Nature Of Control")
    private WebElement natureOfControlLink;
    @FindBy(how = How.LINK_TEXT, using = "add")
    private WebElement addLink;
    @FindBy(how = How.ID, using = "form1:natureOfControlTabSubView:natureOfControlDetails")
    private WebElement nocTableElement;

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

        // wait for the details to be populated
        checkIfTableHasAtLeastOneRowCompleted(nocTableElement);
    }

    private boolean checkIfTableHasAtLeastOneRowCompleted(WebElement nocTableElement) {
        // Locate the table and get all the rows in the table
        List<WebElement> rows = nocTableElement.findElements(By.tagName("tr"));

        // Initialize the flag
        boolean hasCompletedRow = false;

        // Loop through the rows
        for (WebElement row : rows) {
            // Get all the cells in the row
            List<WebElement> cells = row.findElements(By.tagName("td"));

            // Initialize the row completion check
            boolean rowCompleted = true;

            // Loop through the cells
            for (WebElement cell : cells) {
                // Check if the cell has content (non-empty text)
                if (cell.getText().trim().isEmpty()) {
                    rowCompleted = false;
                    break;
                }
            }

            // If a completed row is found, set the flag and break the loop
            if (rowCompleted) {
                hasCompletedRow = true;
                break;
            }
        }

        // Output the result
        if (!hasCompletedRow) {
            throw new RuntimeException("The table does not have any completed rows.");
        }
        return true;
    }

    private void addDetails() {
        addLink.click();
    }

    private void selectNatureOfControl(String natureOfControl) {
        selectByText(natureOfControlField, natureOfControl);
    }

    private void selectNatureOfControlCategory(String natureOfControlCategory) {
        selectByText(natureOfControlCategoryField, natureOfControlCategory, true);
    }
}
