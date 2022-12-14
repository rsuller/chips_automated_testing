package uk.gov.companieshouse.pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import uk.gov.companieshouse.utils.TestContext;

public class ChipsHomePage {

    public TestContext testContext;

    public ChipsHomePage(TestContext testContext) {
        this.testContext = testContext;
        PageFactory.initElements(testContext.getWebDriver(),this);
    }

    @FindBy(how = How.ID, using = "form1:username")
    private WebElement usernameInput;

    @FindBy(how = How.ID, using = "form1:password")
    private WebElement passwordInput;

    @FindBy(how = How.ID, using = "form1:task_authenticate")
    private WebElement loginLink;


    public void logInUser(String username, String password) {
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        loginLink.click();
    }
}
