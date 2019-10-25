/*
 * author PawelPie: Pawel
 * ID: A049473
 */
package com.project.project.components;

import com.applitools.eyes.*;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.project.project.enums.EyesMode;
import com.project.project.enums.HpQcConnectionMode;
import com.project.project.enums.SauceLabsConnectionMode;
import com.project.project.pages.InternetHomePage;
import com.project.project.pages.InternetLoginPage;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class TestBase extends Utils {

    protected Eyes eyes = new Eyes();
    private EyesMode eyesMode = EyesMode.OFF;
    private HpQcConnectionMode hpIntegration = HpQcConnectionMode.OFF;//java 32bits
    static ThreadLocal<String> sessionId = new ThreadLocal<String>();
    static SauceLabsConnectionMode driverType;
    public static TestLog logger;

    static {
        logger = new TestLog();
    }

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> parentTestThreadLocal = new ThreadLocal();
    private HashMap<String, ExtentTest> extentMap = new HashMap();
    public static InternetHomePage homePage;
    public static ThreadLocal<String> categoryGroup = new ThreadLocal<String>();
    private String resolutionSize = "";
    public static ThreadLocal<ExtentTest> child = new ThreadLocal<ExtentTest>();
    public static ThreadLocal<InternetLoginPage> loginPage = new ThreadLocal<InternetLoginPage>();
    //    public static ThreadLocal<HomePage> homePage = new ThreadLocal<HomePage>(); //if we want to use homePage at TestBase.java
    public static ThreadLocal<String> testName = new ThreadLocal<String>();
    protected ThreadLocal<String> testNameParameter = new ThreadLocal<String>();
    static String proxyAddress;
    private String pathFile = "";
    private TimeUnit testDuration;
    String[][] testCaseID = new String[500][2];

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() throws MalformedURLException {
        Utils.beforeConfiguration();
        proxyAddress = getProxyAddress();
    }

    private String getProxyAddress() throws MalformedURLException {
        String PAC_URL = "http://proxyconf.srv.project.com/";
        URL url = null;
        try {
            url = new URL(PAC_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Proxy proxy = ProxyUtil.getProxy(url, new URL("http://@ondemand.saucelabs.com"));
        logger.info(proxy.toString());
        String a1 = String.valueOf(proxy.address());
        return StringUtils.substringBefore(a1, "/");
    }


    @BeforeClass(alwaysRun = true)
    public void logStartTestExecutionBeforeClass() {
        logger.logStartTestClassExecution(getClass().getSimpleName());
        extent = ExtentManager.getInstance();
        parentTestThreadLocal.set(extent.createTest(getClass().getSimpleName()));
        extentMap.put(getClass().getSimpleName(), parentTestThreadLocal.get());
        if (hpIntegration == HpQcConnectionMode.ON) {
        }
        if (eyesMode == EyesMode.ON) {
            eyes.setProxy(new ProxySettings("http://" + proxyAddress + ":8080"));
            // This is your api key, make sure you use it in all your tests.
            eyes.setApiKey("1xnV1010103H7zX5JId51VXt8JiXYYVup1p6wYjaLv0j5mk110");
            eyes.setForceFullPageScreenshot(true);
            eyes.setStitchMode(StitchMode.CSS); //if your page has a ribbon/menu which
            eyes.setHideScrollbars(true); //to hide scrollbar during taking the screenshot
            eyes.setMatchLevel(MatchLevel.LAYOUT2); //for dynamic pages
            eyes.setBaselineEnvName("webVisualTests");
        }
    }


    @BeforeMethod(alwaysRun = true)
    public void logStartTestExecutionBeforeMethod(Method testMethod) {
        logger.logStartTestExecution(testMethod.getName());
        setTestCategory(testMethod);
        testName.set(getClass().getSimpleName() + "." + testMethod.getName());
        loginPage.set(PageFactory.initElements(chooseAndLaunchBrowser(), InternetLoginPage.class));
        if (resolutionSize.equals("")) {
            resolutionSize = driver.get().manage().window().getSize().toString();
        }
        if (eyesMode == EyesMode.ON) {
            int width = resolutionSize.indexOf("{");
            int height = resolutionSize.indexOf(",");
            eyes.open(driver.get(), "web example", testMethod.getName(), new RectangleSize(width, height));
        }
    }

    private void setTestCategory(Method testMethod) {
        Test t = testMethod.getAnnotation(Test.class);
        try {
            categoryGroup.set(t.groups()[0]);
        } catch (ArrayIndexOutOfBoundsException e) {
            categoryGroup.set("uknownCategory");
        }
    }


    @AfterMethod(alwaysRun = true)
    public void logEndTestExecutionAfterMethod(ITestResult result, Method testMethod) throws IOException {
        child.set(extentMap.get(getClass().getSimpleName()).createNode(testMethod.getName() + dataProviderString()));
        child.get().assignCategory(categoryGroup.get());
        readTestStatusAndLogResults(result, testMethod);
        try {
            System.getProperty("ActualVersion");
//            homePage.logout();
        } catch (NullPointerException ignored) {
        }
//        try {
//            homePage.acceptAlert();
//        } catch (TimeoutException ignored) {
//        }
        if (hpIntegration == HpQcConnectionMode.ON) {
            //HP QC connection
            initUpdateTestResultsinHPQC();
        }
        if (eyesMode == EyesMode.ON) {
            try {
                // End visual testing. Validate visual correctness.
                eyes.close();
            } finally {
                // Abort test in case of an unexpected error.
                eyes.abortIfNotClosed();
            }
        }
        driver.get().quit();
    }

    private void readTestStatusAndLogResults(ITestResult result, Method testMethod) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            new File("/target/test-screenshots/").mkdirs();
            if (eyesMode == EyesMode.OFF) {
                File screenshot = ((TakesScreenshot) driver.get()).getScreenshotAs(OutputType.FILE);
                String pathDir = new File("").getAbsolutePath();
                pathFile = pathDir + "\\target\\test-screen\\" + result.getMethod().getMethodName() +testNameParameter.get()+ "-screenshot.png";
                File targetFile = new File(pathFile);
                FileUtils.copyFile(screenshot, targetFile);
                child.get().addScreenCaptureFromPath(pathFile);
            }
            child.get().log(Status.FAIL, "Test || " + testMethod.getName()+dataProviderString() + "|| FAILED with message: " + result.getThrowable().getMessage());
            //child.log(LogStatus.INFO, "Test was performed by admin: " + adminId);
            child.get().log(Status.INFO, "Test environment: " + readEnv());
            if (System.getProperty("browserInfo") == null) {
                Capabilities cap = ((RemoteWebDriver) driver.get()).getCapabilities();
                System.setProperty("browserInfo", cap.getBrowserName() + " ver." + cap.getVersion());
            }
            try {
                child.get().log(Status.INFO, "URL in the End of Test: " + driver.get().getCurrentUrl());
            } catch (UnsupportedCommandException ignored) {
            }
        }
        if (result.getStatus() == ITestResult.SUCCESS) {
            child.get().log(Status.PASS, "Test PASSED: " + testMethod.getName());
        }
        if (result.getStatus() == ITestResult.SKIP) {
            child.get().log(Status.SKIP, "Test SKIPPED");
        }

        logger.logEndTestExecution(testMethod.getName()+dataProviderString(), result.getStatus());
        if (driverType == SauceLabsConnectionMode.ON) {
            Boolean passed = result.getStatus() == ITestResult.SUCCESS;
            try {
                // Logs the result to Sauce Labs
                ((JavascriptExecutor) driver.get()).executeScript("sauce:job-result=" + (passed ? "passed" : "failed"));
                ((JavascriptExecutor)driver.get()).executeScript("sauce:job-name="+getClass().getSimpleName() + "." + testMethod.getName() + dataProviderString());
            } catch (NoClassDefFoundError e) {
                child.get().log(Status.INFO, "Sessiuon notFound exception occurs during SauceConnection");
            }
        }
    }


    @AfterClass(alwaysRun = true)
    public void logEndTestExecutionAfterClass() {
        logger.logEndTestClassExecution(getClass().getSimpleName());
    }

    @AfterSuite(alwaysRun = true)
    public void logEndTestExecutionAfterSuite() throws IOException, InterruptedException {
        addInformationToExtentReport();
        extent.flush();
        Utils.killDrivers();
    }

    private void addInformationToExtentReport() throws NullPointerException {
        //EmailReporter emailReporter = new EmailReporter(emailReportFile); if you want to send report by Email
        extent.setSystemInfo("APP Version", System.getProperty("ActualVersion"));
        extent.setSystemInfo("Test Environment: ", System.getProperty("testedEnv"));
        extent.setSystemInfo("Browser: ", System.getProperty("browserInfo"));
        extent.setSystemInfo("Browser Resolution: ", resolutionSize);
        extent.setSystemInfo("Java Version : ", System.getProperty("java.version"));
        if (driverType == SauceLabsConnectionMode.OFF) {
            extent.setSystemInfo("OS : ", System.getProperty("os.name"));
            extent.setSystemInfo("OS Architecture : ", System.getProperty("os.arch"));
            extent.setSystemInfo("User Name : ", System.getProperty("user.name"));
            extent.setSystemInfo("Machine Name : ", System.getProperty("machine.name"));
            extent.setSystemInfo("IP Address : ", System.getProperty("machine.address"));
            extent.setSystemInfo("Execution Machine: ", "Local");
            //extent.attachReporter(htmlReporter, emailReporter);
        } else {
            extent.setSystemInfo("Execution Machine: ", "SauceLabs");
        }
    }
    private String dataProviderString() {
        if (testNameParameter.get() != null) {
            return " (" + testNameParameter.get() + ")";
        }
        return "";
    }
    private void initUpdateTestResultsinHPQC() {
        HpConnection hpConnection = new HpConnection();
        hpConnection.connectionToQC();
        hpConnection.addFoldername(readEnv());
    }

}
