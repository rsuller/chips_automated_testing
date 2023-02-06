# chips_automated_testing
Automated Tests for CHIPS using Selenium 4

### Before running tests

Browser sign in is enabled by default on Companies House Windows machines. However, we cannot run automated tests without disabling this feature. It is necessary to do this through 
the registry, by setting the Edge browserSignIn d-word value to 0. A Powershell script has been written to perform this action and can be run by following the below:

( in IntelliJ ) Right click on browserSignIn.ps1 and select Show in Explorer, alternatively you can navigate to top directory of the project manually, same level as pom.xml
At this point you can now right-click and select the option Run with Powershell
After few seconds the script should execute. You maybe prompted to accept Powershell making changes to your machine. Click yes to allow this if so
and set the registry value to 0. You can check this by navigating to the registry editor from the start menu

From time to time and after restarting your machine, this value can be reset automatically. When running tests, if you see the Microseft Edge sign in prompt it is necessary to re-run 
the Powershell script using the instructions above

### How to run the tests
e.g. `mvn clean test -Dcucumber.options="--tags @example" -Denv=testing`

See [Cucumber.io](https://cucumber.io/docs/cucumber/) for details on the Cucumber Framework & cucumber options.

Maven requires an argument for 'env' - see `conf/env.conf` for available options.