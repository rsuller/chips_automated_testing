package uk.gov.companieshouse.pageobjects.workitems;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.utils.ElementInteraction;
import uk.gov.companieshouse.utils.TestContext;


public class TeamWorkPage extends ElementInteraction {
    @FindBy(how = How.ID, using = "teamQueueForm:dropdown:field")
    private WebElement taskFilterColumnSelect;
    @FindBy(how = How.ID, using = "teamQueueForm:task_filter")
    private WebElement taskQueueFilterLink;
    @FindBy(how = How.ID, using = "form1:task_viewTeamWork")
    private WebElement teamWorkSummaryLink;
    @FindBy(how = How.ID, using = "teamQueueForm:workManagementTabs:task_viewWork")
    private WebElement teamWorkTabLink;
    @FindBy(how = How.CSS, using = "table[id='teamQueueForm:workObjects'] tbody tr")
    private WebElement workObjectRows;
    @FindBy(how = How.ID, using = "teamQueueForm:workObjects:sortByStartDate")
    private WebElement receiptDateFilterLink;
    @FindBy(how = How.ID, using =  "teamQueueForm:task_allocate")
    private WebElement allocateWorkObjectLink;
    @FindBy(how = How.ID, using =  "form1:task_selectedUser:field")
    private WebElement allocateUserSelect;
    @FindBy(how = How.ID, using =  "form1:task_save")
    private WebElement allocateWorkSaveButton;
    @FindBy(how = How.ID, using = "teamQueueForm:task_teamFilterValue:field")
    private WebElement filterValueSelect;
    @FindBy(how = How.ID, using = "teamQueueForm:task_custom:field")
    private WebElement customValueEntryField;
    private static final Logger LOG = LoggerFactory.getLogger(TeamWorkPage.class);
    public final TestContext testContext;

    /**
     * Required constructor for class.
     */
    public TeamWorkPage(TestContext testContext) {
        super(testContext);
        this.testContext = testContext;
        PageFactory.initElements(testContext.getWebDriver(), this);
        }

    /**
     * Go to the team work summary page and filter by company number.
     *
     * @param companyNumber
     *            the company name
     */
    public TeamWorkPage filterTeamWorkSummaryByCompanyNumberAndFormType(String companyNumber, String formType) {
        int tries = 1;
        int maxTries = 15;
        clickTeamWorkSummaryLink();
        while (true) {
            LOG.info("Attempting to find work item for {} company number {}, attempt {} of {}",
                    formType, companyNumber, tries, maxTries);
            if (tries == maxTries) {
                LOG.error("Failed to find work item after {} retry attempts", tries);
                throw new RuntimeException(
                        "Company " + companyNumber + " didn't appear in the Team Work Summary");
            }
            clickTeamWorkTab().selectFilterByOption("company number");
            enterFilterValue(companyNumber).clickFilterButton();
            try {
                // Check the wlist of work objects is not empty
                workObjectRows.isEnabled();
                // Get the work objects as a list of web elements and attempt to click the latest.
                List<WebElement> workItemRows = getWorkObjectRowsByDocumentType(formType);
                workItemRows.get(0).click();
                break;
            } catch (NoSuchElementException | IndexOutOfBoundsException exception) {
                // wait 3 seconds before continuing
                getWebDriverWait(3);
                tries++;
            }
        }
        LOG.info("Work items for company number {} found. Continuing with test", companyNumber);

        return this;
    }

    /**
     * allocate most recent work history.
     */
    public TeamWorkPage allocateMostRecentWorkObjectSelected(String type, String userName) {
                clickAllocateLink();
                selectUserFromOptions(userName);
                clickAllocateWorkSaveButton();
        LOG.info("Work item {} allocated to {}. Continuing with test", type, userName);
        return this;
    }

    private TeamWorkPage clickTeamWorkSummaryLink() {
        waitUntilElementDisplayed(teamWorkSummaryLink);
        teamWorkSummaryLink.click();
        waitUntilElementDisplayed(teamWorkTabLink);
        return this;
    }

    private TeamWorkPage clickTeamWorkTab() {
        teamWorkTabLink.click();
        waitUntilElementDisplayed(taskFilterColumnSelect);
        return this;
    }

    private TeamWorkPage selectFilterByOption(String column) {
        selectByText(taskFilterColumnSelect, column);
        return this;
    }

    /**
     * returns a selected filter value.
     */
    private TeamWorkPage enterFilterValue(String value) {
        waitUntilElementDisplayed(customValueEntryField);
        typeText(customValueEntryField, value);
        return this;
    }

    private TeamWorkPage clickFilterButton() {
        taskQueueFilterLink.click();
        waitUntilElementDisplayed(taskQueueFilterLink);
        return this;
    }

    private List<WebElement> getWorkObjectRowsByDocumentType(String type) {
        final List<WebElement> rows = new ArrayList<>();
        final List<WebElement> all = testContext.getWebDriver().findElements(By.cssSelector("table[id='teamQueueForm:workObjects'] tbody tr"));
        final String idFormat = "teamQueueForm:workObjects:%d:documentContactTypeColumn";
        final String assignedToFormat = "teamQueueForm:workObjects:%d:assignedToColumn";
        WebElement webElement;
        for (int i = 0; i < all.size(); i++) {
            webElement = all.get(i);
            final String columnType
                    = webElement.findElement(By.id(String.format(idFormat, i))).getText().trim();
            final String columnAssignedTo
                    = webElement.findElement(By.id(String.format(assignedToFormat, i))).getText().trim();
            if (columnType.equals(type) && columnAssignedTo.equals("unassigned")) {
                rows.add(webElement);
            }
        }
        return rows;
    }

    private TeamWorkPage clickAllocateLink() {
        allocateWorkObjectLink.click();
        waitUntilElementDisplayed(allocateUserSelect);
        return this;
    }

    private TeamWorkPage selectUserFromOptions(String userName) {
        selectByText(allocateUserSelect, userName);
        return this;
    }

    private TeamWorkPage clickAllocateWorkSaveButton() {
        allocateWorkSaveButton.click();
        return this;
    }

}
