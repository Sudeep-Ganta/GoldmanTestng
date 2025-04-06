package com.goldman.selenium;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.util.Properties;

public class ExtentReportListener {

    private static ExtentReports extent;  // Static instance of ExtentReports

    // Initialize ExtentReports if not already initialized
    public static ExtentReports getExtentReports() {
        if (extent == null) {
            // Initialize the ExtentSparkReporter and set the report file path
            ExtentSparkReporter htmlReporter = new ExtentSparkReporter("D:\\Users\\SHGanta\\eclipse-workspace\\Goldman_Testing\\Reports\\extent-report.html");
         // Set report name, document title and enable dark mode
            htmlReporter.config().setReportName("Manager Test Execution Report");
            htmlReporter.config().setDocumentTitle("Manager Test Results");
            htmlReporter.config().setTheme(Theme.DARK);  // Enable Dark Mode

            // Add custom CSS to the report (optional)
            String css = "<style>" +
                         "body {background-color: #2c2c2c; font-family: 'Arial', sans-serif;}" +
                         "h1, h2 {color: white;}" +
                         "footer {color: white; background-color: #212121; padding: 10px; text-align: center;}" +
                         "</style>";
            htmlReporter.config().setCss(css);

            // Enable Dark Mode
            htmlReporter.config().setTheme(Theme.DARK);

            // Initialize the ExtentReports instance and attach the reporter
            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);

            // Add system information (OS, Java, etc.)
            addSystemInfo();
        }
        return extent;
    }

    // Add system information to the report (OS, Java Version, etc.)
    private static void addSystemInfo() {
        Properties systemProperties = System.getProperties();
        extent.setSystemInfo("OS", systemProperties.getProperty("os.name"));
        extent.setSystemInfo("Java Version", systemProperties.getProperty("java.version"));
        extent.setSystemInfo("User Name", System.getenv("USERPROFILE"));
        extent.setSystemInfo("Environment", "QA Automation");
    }

    // This method flushes the report at the end of test execution
    public static void flushReport() {
        if (extent != null) {
            extent.flush(); // Write the results to the HTML file
        }
    }
}
