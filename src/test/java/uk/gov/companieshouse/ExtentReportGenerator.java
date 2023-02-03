package uk.gov.companieshouse;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;

public class ExtentReportGenerator {

    public static ExtentReports getExtentReport() {
        ExtentReports report = new ExtentReports();

        File extentReportFile = new File(System.getProperty("user.dir") + "\\ExtentReports\\test_results_report.html");

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(extentReportFile);

        report.attachReporter(sparkReporter);

        return report;
    }
}
