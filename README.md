# chips_automated_testing
Automated Tests for CHIPS using Selenium 4

### Before running tests

Browser sign-in is enabled by default on Companies House Windows machines. However, we cannot run automated tests without disabling this feature. It is necessary to do this through the registry, by setting the Edge browserSignIn d-word value to 0. A Powershell script has been written to perform this action and can be run by following the below:

Go to Start Menu -> Company Portal. There you should have an app called Browser Sign-In Bypass:

Open this app and install it (or reinstall when you want it to run again).  This will set the appropriate browsersignin flag to 0. You can check this by navigating to the registry editor from the start menu

From time to time and after restarting your machine, this value can be reset automatically. When running tests, if you see the Microsoft Edge sign-in prompt it is necessary to reinstall the app using the instructions above


### How to run the tests
e.g. `mvn clean test -Dcucumber.filter.tags="@example" -Denv=testing`

See [Cucumber.io](https://cucumber.io/docs/cucumber/) for details on the Cucumber Framework & cucumber options.

Maven requires an argument for 'env' - see `conf/env.conf` for available options.

### Sharing State
Sharing state is something that is required in Cucumber Frameworks to pass the state between implemented step definitions.
In this project you will notice that it uses Pico Container as the Dependency Injection framework as recommended at 
[Cucumber.io](https://cucumber.io/docs/cucumber/state/?lang=java)

Within the framework you will see the class `TestContext.java` (in the '/utils' package), this class is used to 'set' and 'get' test data that you may require throughout your test.

e.g.
```
   @Before
    public void setUp() {
        testContext.getWebDriver().get(
                testContext.getEnv().config.getString("chips_url"));
    }
```

### Contributing
Please follow the standard protocol for adding Pull Requests.
We ask that you adhere to any instruction in the `PULL_REQUEST_TEMPLATE.md` file.

=======

### Checkstyle in IntelliJ
Checkstyle has been enabled in this project using Companies House Java checkstyle config [here](https://github.com/companieshouse/java-checkstyle-config)

To enable this in IntelliJ:
- File -> settings -> plugins
- install the Plugin - CheckStyle-IDEA. Restart IDE when complete.
- File -> settings -> checkstyle:
   - set Checkstyle version back to 8.10.1
   - Add path to configuration file `companieshouse_checks.xml` with a suitable description. E.g. CH checkstyle
   - Violations will be flagged when running tests through the command line or can be run in IntelliJ from the Checkstyle tab

### Feature/Scenario Tagging
All tags should be lowercase, with underscores used for any spaces between words. The following guidelines should 
be adhered to at the feature/scenario level:

Feature tags:
1. (Required) The section of Chips corresponds to the feature. For
   example `@insolvency/@company_addresses/@company_officers`
2. (Optional) the sub-section above that the feature corresponds to, if
   applicable. For example:
   `@liquidation/@administration/@receivership`
3. (Required) The form(s) that are covered by the scenarios within the
   feature file. For example:
   `@liq13 @liq14`

Scenario tags:
1. (required) The filing method of the scenario under test. For example:
   `@electronic/@fes_scanned/@paper`
2. (optional) The specific form tag is being tested in the scenario. Useful
   if more than one form type is being tested within the feature. For example:
   `@liq13/@liq14`


### Refactoring
When refactoring code, please ensure that you run the tests and checkstyle before committing your changes.

Please refactor code to the highest standard possible, and ensure that you are following the 
[Selenium best practices](https://www.selenium.dev/documentation/en/guidelines_and_recommendations/).

Refactoring is essential for code maintenance and project sustainability. 
It involves restructuring code without altering its external behaviour, 
improving readability and maintainability. Regular refactoring reduces technical debt, 
simplifies complexity, and enhances code quality. It facilitates collaboration and eases modification and debugging, 
reducing the risk of introducing bugs. By keeping the codebase flexible and efficient, refactoring ensures 
long-term project viability.
