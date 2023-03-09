# chips_automated_testing
Automated Tests for CHIPS using Selenium 4

### Before running tests

Browser sign-in is enabled by default on Companies House Windows machines. However, we cannot run automated tests without disabling this feature. It is necessary to do this through the registry, by setting the Edge browserSignIn d-word value to 0. A Powershell script has been written to perform this action and can be run by following the below:

( In IntelliJ ) Right-click on browserSignIn.ps1 and select Show in Explorer, alternatively you can navigate to the top directory of the project manually, at the same level as pom.xml. At this point, you can now right-click and select the option Run with Powershell. After a few seconds the script should execute. You may be prompted to accept Powershell making changes to your machine. Click yes to allow this if so and this should set the registry value to 0, which disables browser sign-in You can check this by navigating to the registry editor from the start menu.

From time to time and after restarting your machine, this value can be reset automatically. When running tests, if you see the Microsoft Edge sign-in prompt it is necessary to re-run the Powershell script using the instructions above

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
### Checkstyle in IntelliJ
Checkstyle has been enabled in this project using Companies House java-checkstyle-config.

To enable this in IntelliJ:
- File -> settings -> plugins
- install the Plugin - CheckStyle-IDEA. Restart IDE when complete.
- File -> settings -> checkstyle:
   - set Checkstyle version back to 8.10.1
   - Add path to configuration file `companieshouse_checks.xml` with a suitable description. E.g. CH checkstyle
   - Violations will be flagged when running tests through teh command line or can be run in IntelliJ from the Checkstyle tab
