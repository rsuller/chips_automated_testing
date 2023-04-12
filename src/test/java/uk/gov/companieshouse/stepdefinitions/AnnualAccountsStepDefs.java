package uk.gov.companieshouse.stepdefinitions;

import io.cucumber.java.en.When;
import java.util.List;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.pageobjects.AnnualAccountsPage;
import uk.gov.companieshouse.utils.TestContext;


public class AnnualAccountsStepDefs {

    public TestContext context;
    public AnnualAccountsPage annualAccountsPage;
    public static final Logger log = LoggerFactory.getLogger(AnnualAccountsStepDefs.class);


    /**
     * Required constructor for class.
     */
    public AnnualAccountsStepDefs(TestContext context, AnnualAccountsPage annualAccountsPage) {
        this.context = context;
        this.annualAccountsPage = annualAccountsPage;
    }

    /**
     * Completes the mandatory details to file accounts. Selects an account type at random from the table
     * displayed in the feature file.
     */
    @When("I complete mandatory details to file accounts for a company")
    public void inputMandatoryDetailsForALtdCompany(List<String> accountsTypeTable) {
        Random random = new Random();
        String accountsType = accountsTypeTable.get(random.nextInt(accountsTypeTable.size()));
        log.info("Attempting to file {} accounts...", accountsType);
        String nextAccountsMud = annualAccountsPage.readNextAaMudDueFromScreen();
        annualAccountsPage
                .enterAccountsMud(nextAccountsMud)
                .selectAccountsType(accountsType)
                .saveForm();
    }

}
