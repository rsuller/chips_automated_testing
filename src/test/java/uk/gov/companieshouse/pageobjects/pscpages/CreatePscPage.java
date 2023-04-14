package uk.gov.companieshouse.pageobjects.pscpages;

import uk.gov.companieshouse.testdata.datamodel.PersonOfSignificantControl;
import uk.gov.companieshouse.utils.TestContext;

public class CreatePscPage extends PscAddressDetails {

    private final PscDetailsTab pscDetailsTab;
    private final PscUraTab pscUraTab;
    private final PscNatureOfControlTab pscNatureOfControlTab;
    private final PscRegisterDateTab pscRegisterDateTab;

    /**
     * Required constructor for class.
     *
     * @param testContext required for shared state.
     */
    public CreatePscPage(TestContext testContext,
                         PscDetailsTab pscDetailsTab,
                         PscUraTab pscUraTab,
                         PscNatureOfControlTab pscNatureOfControlTab,
                         PscRegisterDateTab pscRegisterDateTab) {
        super(testContext);
        this.pscDetailsTab = pscDetailsTab;
        this.pscUraTab = pscUraTab;
        this.pscNatureOfControlTab = pscNatureOfControlTab;
        this.pscRegisterDateTab = pscRegisterDateTab;
    }
    public void enterNewPscDetails(PersonOfSignificantControl psc) {
        pscDetailsTab.enterPscDetails(psc);
        pscUraTab.enterUraDetails(psc);
        pscNatureOfControlTab.enterPscNatureOfControlDetails(psc);
        pscRegisterDateTab.enterPscRegisterDate(psc);
    }
}
