package uk.gov.companieshouse;

import com.profesorfalken.jpowershell.PowerShell;
import com.profesorfalken.jpowershell.PowerShellNotAvailableException;
import com.profesorfalken.jpowershell.PowerShellResponse;
import io.cucumber.java.After;
import io.cucumber.java.Before;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.utils.TestContext;

public class Hooks {

    public static final Logger log = LoggerFactory.getLogger(Hooks.class);
    public TestContext testContext;

    public Hooks(TestContext testContext) {
        this.testContext = testContext;
    }

    @Before
    public void setUp() {

        try (PowerShell powerShell = PowerShell.openSession()) {
            String script = "/browserSignIn.ps1";
            //Read the resource
            BufferedReader srcReader = new BufferedReader(
                    new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream(script))));
            PowerShellResponse response = powerShell.executeScript(srcReader);
            log.info("Powershell script executed successfully");
        } catch (PowerShellNotAvailableException ex) {
            throw new RuntimeException("Powershell not available");
        }
        testContext.getWebDriver().get(
                testContext.getEnv().config.getString("chips_url"));

    }

    @After
    public void tearDown() {
        testContext.getWebDriver().close();
    }
}
