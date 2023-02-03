package uk.gov.companieshouse;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.junit.platform.commons.util.StringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import uk.gov.companieshouse.utils.TestContext;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Hooks {
    public TestContext testContext;
    ExtentReports report = ExtentReportGenerator.getExtentReport();
    ExtentTest extentTest;

    public Hooks(TestContext testContext) {
        this.testContext = testContext;
    }

    @Before
    public void setUp(Scenario scenario) throws IOException {
        //Remove locally stored failed images
        FileUtils.deleteDirectory(new File("extentreports/failedtestscreenshots/"));

        // Set up Extent Test
        extentTest = report.createTest(scenario.getName());
        extentTest.log(Status.INFO, scenario.getName() + " has started.");

        // Go to CHIPS
        testContext.getWebDriver().get(
                testContext.getEnv().config.getString("chips_url"));
    }

    @After
    public void tearDown(Scenario scenario) throws IOException {
        if (scenario.isFailed()) {
            String screenshot = getScreenshot(testContext.getWebDriver(),
                    StringUtils.replaceWhitespaceCharacters(scenario.getName(), ""));
            extentTest.log(Status.FAIL, scenario.getName() + " has failed.").addScreenCaptureFromPath(screenshot)
                    .fail(MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());

        }
        testContext.getWebDriver().quit();
        report.flush();
    }

    public static String getScreenshot(WebDriver webDriver, String screenshotName) throws IOException {
        String dateName = new SimpleDateFormat("ddMMyyyyhhmm").format(new Date());
        TakesScreenshot screenshot = (TakesScreenshot) webDriver;

        File source = screenshot.getScreenshotAs(OutputType.FILE);

        String destination = System.getProperty("user.dir") + "/extentreports/failedtestscreenshots/" + screenshotName
                + dateName + ".png";
        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);

        return destination;
    }

}
