package uk.gov.companieshouse.stepdefinitions;

import io.cucumber.java.en.When;
import uk.gov.companieshouse.pageobjects.companysearch.CompanyDetailsScreen;
import uk.gov.companieshouse.testdata.CompanyDetails;
import uk.gov.companieshouse.testdata.DocumentDetails;


public class NewIncorporationStepDefs {
    private final CompanyDetails companyDetails;
    private final CompanyDetailsScreen companyDetailsScreen;
    private final DocumentDetails documentDetails;


    /**
     * Required constructor for class.
     */
    public NewIncorporationStepDefs(CompanyDetails companyDetails, CompanyDetailsScreen companyDetailsScreen,
                                    DocumentDetails documentDetails) {
        this.companyDetails = companyDetails;
        this.companyDetailsScreen = companyDetailsScreen;
        this.documentDetails = documentDetails;
    }

    /**
     * Check the company enquiry screen new incorporation.
     */
    @When("the new company is displayed correctly")
    public void checkCompanyNameAndTransactions() {
                companyDetailsScreen
                        .verifyNewCompanyName(companyDetails.getCompanyObject().getName() + " "
                                + companyDetails.getCompanyObject().getNameEnding())
                .getExpectedTransactionFromHistory("ND", documentDetails.getReceivedDate(),
                "REQUESTED", "NEW DIRECTOR LETTER (ND)")
                        .getExpectedTransactionFromHistory("MODEL ARTICLES", documentDetails.getReceivedDate(),
                                "ACCEPTED", "MODEL ARTICLES ADOPTED");
    }

}
