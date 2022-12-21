package uk.gov.companieshouse.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.pageObjects.ProcessStartOfDocumentPage;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class GlobalNavBar extends ElementInteraction {

    public static final Logger log = LoggerFactory.getLogger(ProcessStartOfDocumentPage.class);
    public TestContext testContext;

    @FindBy(how = How.CSS, using = "div[class='bannerrow'] > [class='breadleft']")
    private WebElement elementBanner;

    // xpath is used here as previous method of retaining all .item elements was costly
    // and time consuming this speeds up menu selection significantly
    @FindBy(how = How.XPATH, using = "//td[contains(@class, 'label')][contains(text(), 'menu')]")
    private WebElement mainMenuElement;

    @FindBy(how = How.CSS, using=  "div[class='bannerrow']>[class='bannerright']>"
            + "[title='Process Start of Document']")
    private WebElement statusLink;



    public GlobalNavBar(TestContext testContext) {
        super(testContext);
        this.testContext = testContext;
        PageFactory.initElements(testContext.getWebDriver(),this);
    }

    public boolean orgUnitSelected(String orgUnit) {
        return getBannerText().contains(orgUnit);
    }

    public GlobalNavBar clickChangeOrgUnitLabel() {
        return clickNavBarElement("Change Org Unit");
    }

    public GlobalNavBar clickMenuLabel() {
        return clickNavBarElement("menu");
    }

    public GlobalNavBar clickProcessFormLabel() {
        return clickNavBarElement("process form");
    }

    /**
     * Attempt to click the logout button post test. Throw a Chips exception in the cases where
     * the button is not available, e.g. when the service is down or the test fails to login.
     */
    public GlobalNavBar clickLogoutLabel() {
        try {
            clickNavBarElement("logout");
        } catch (NoSuchElementException ex) {
            throw new RuntimeException("Logout button was not available. Check donut report or "
                    + "test video for details");
        }
        return this;
    }

    public GlobalNavBar clickStatusLink() {
        getWebDriverWait(5).until(visibilityOf(mainMenuElement));
        statusLink.click();
        return this;

    }

    private String getBannerText() {
        return elementBanner.getText();
    }

    /**
     * Click one of the global nav items e.g. 'menu', 'enquiry'
     *
     * @param label the text value
     */
    private GlobalNavBar clickNavBarElement(String label) {
        getMenuItem(label).click();
        return this;
    }

    /**
     * Wait for main menu nav bar to be displayed.
     *
     */
    public GlobalNavBar waitUntilDisplayed(final int timeout) {
        getWebDriverWait(timeout).until(visibilityOf(mainMenuElement));
        try {
            mainMenuElement.isDisplayed();
        } catch (NoSuchElementException exception) {
            log.error("Main menu bar was not displayed.");
            throw new NoSuchElementException("Main menu bar "
                    + "was not displayed", exception);
        }
        return this;
    }

    /**
     * Click a first level item in the 'menu' navigation.
     *
     * @param menuItem the text value in the menu list
     */
    public GlobalNavBar clickMenuItem(String menuItem) {
        return clickMenuLabel()
                .mouseOverMenuItem(menuItem)
                .clickMenuItemElement(menuItem);
    }

    /**
     * Select a 2nd level item from the 'menu'.
     *
     * @param menuItem    the first level item
     * @param subMenuItem the second level item
     */
    public GlobalNavBar clickSubMenuItem(String menuItem, String subMenuItem) {
        return clickMenuLabel()
                .mouseOverMenuItem(menuItem)
                .mouseOverMenuItem(subMenuItem)
                .clickMenuItemElement(subMenuItem);
    }

    /**
     * Hover over a item in the 'menu'. This can be used for all levels as the selector is the same.
     * Note: this expects that you have already called mouseOverMenuElement() first.
     *
     * @param menuItem the menu item text
     */
    private GlobalNavBar mouseOverMenuItem(String menuItem) {
        WebElement element = getMenuItem(menuItem);
        Actions actions = new Actions(testContext.getWebDriver());
        actions.moveToElement(element).build().perform();
        return this;
    }

    /**
     * Click on a menu item.
     *
     * @param menuItem the menu item text value
     */
    private GlobalNavBar clickMenuItemElement(String menuItem) {
        getMenuItem(menuItem).click();
        return this;
    }

    /**
     * Dynamically select a WebElement from the menu by its text value.
     *
     * @param textValue the menu item text value
     * @return the selenium web element.
     */
    private WebElement getMenuItem(String textValue) {
        String xpath = "//td[contains(@class, 'label')][contains(text(), '" + textValue + "')]";
        WebElement element = testContext.getWebDriver().findElement(By.xpath(xpath));

        if (element == null) {
            throw new RuntimeException(
                    "Unable to locate menu item with a text value of '" + textValue + "'");
        }

        return element;
    }
}
