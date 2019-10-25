package com.project.project.components;

import com.project.project.enums.SauceLabsConnectionMode;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.project.project.components.TestBase.*;


/**
 * Utility class for test automation scripts.
 *
 * @author PawelPie
 */
public class Utils {

    private static final int SLEEP_INTERVAL = 5000;
    private static final String IE_BROWSER = "internet explorer";
    private static final String CHROME_BROWSER = "chrome";
    private static final String FIREFOX_BROWSER = "firefox";

    //TEST RUN PARAMETERS
    private static String userID = System.getenv("PROXY_USERID");
    private static String vcnPass = System.getenv("PROXY_VCNPASS");
    private static String username = System.getProperty("SAUCE_USERNAME");
    private static String accessKey = System.getProperty("SAUCE_ACCESSKEY");
    private static String SAUCE_CONNECTION = System.getProperty("SAUCE_CONNECTION");
    //    private static String SAUCE_CONNECTION = SauceLabsConnectionMode.ON.toString();
    private static String BROWSER_TYPE = System.getProperty("BROWSER_TYPE");
    private static String BROWSER_VERSION = System.getProperty("BROWSER_VERSION");
    private static String BROWSER_PLATFORM = System.getProperty("BROWSER_PLATFORM");


    public static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
    public static String startTimestamp = "";


    static String readEnv() {
        String env;
        try {
            if (driver.get().getCurrentUrl().contains("system-qa.")) {
                env = "INTERNET QA";
            } else if (driver.get().getCurrentUrl().contains("system-test")) {
                env = "INTERNET TEST";
            } else if (driver.get().getCurrentUrl().contains("system-dev")) {
                env = "INTERNET DEV";
            } else {
                env = "uknownEnvironment";
            }
        } catch (NullPointerException e) {
            env = "CLOSED!";
        }
        if (System.getProperty("testedEnv") == null || System.getProperty("testedEnv").equals("uknownEnvironment")) {
            System.setProperty("testedEnv", env);
        }
        return env;
    }

    /**
     * @return Timestamp in "yyyy-MM-dd'T'HH:mm:ss.SSS" format
     */
    private static String currentTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(new Date());
    }


    /**
     * @return today date in yyyy-MM-dd format as a String
     */
    public static String getTodayDate() {
        String dateFormat = "M/dd/yyyy";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(cal.getTime());
    }

    /**
     * @return current year as a String
     */
    public static String getCurrentYear() {
        return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    }

    public static String getRandomString(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    /**
     * @param maxValue
     * @return random integer value from following range <1, @param maxValue>
     */
    public static int getRandomInt(int maxValue) {
        Random generator = new Random();
        return generator.nextInt(maxValue) + 1;
    }

    public static void killDrivers() throws IOException, InterruptedException {
        if (BROWSER_TYPE.equals(IE_BROWSER)) {
            Runtime.getRuntime().exec("taskkill /F /IM IEdriverServer.exe");
            Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
        }
        if (BROWSER_TYPE.equals(CHROME_BROWSER)) {
            Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
            Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
            Runtime.getRuntime().exec("taskkill /F /IM chromedriver_v2.2.exe");
            Runtime.getRuntime().exec("taskkill /F /IM chromedriver_win_32.exe");
        }
        if (BROWSER_TYPE.equals(FIREFOX_BROWSER)) {
            Runtime.getRuntime().exec("taskkill /F /IM firefox.exe");
        }
        Thread.sleep(SLEEP_INTERVAL);
    }

    /**
     * Initiates the Webdriver.get()
     *
     * @return the driver.get() for a specified browser
     */
    WebDriver chooseAndLaunchBrowser() {
        if (SAUCE_CONNECTION == null || SAUCE_CONNECTION.equals(SauceLabsConnectionMode.OFF.toString())) {
            driverType = SauceLabsConnectionMode.OFF;
            if (BROWSER_TYPE == null) {
                BROWSER_TYPE = "chrome";
            }
            if ((FIREFOX_BROWSER).equals(BROWSER_TYPE)) {
                driver.set(new FirefoxDriver());
                driver.get().manage().window().maximize();
                driver.get().manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            }
            if ((CHROME_BROWSER).equals(BROWSER_TYPE)) {
                driver.set(new ChromeDriver());
                driver.get().manage().window().maximize();
                driver.get().manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            }
            if ((IE_BROWSER).equals(BROWSER_TYPE)) {
                DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
                capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                capabilities.setCapability("ignoreProtectedModeSettings", true);
                driver.set(new InternetExplorerDriver(capabilities));
                driver.get().manage().window().maximize();
                driver.get().manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            }
        }
        if (SAUCE_CONNECTION != (null) && SAUCE_CONNECTION.equals(SauceLabsConnectionMode.ON.toString())) {
            driverType = SauceLabsConnectionMode.ON;
            //create sauceLabs webdriver.get() session
            if (BROWSER_TYPE == null) {
                BROWSER_TYPE = "chrome";
            }
            if (BROWSER_VERSION == null) {
                BROWSER_VERSION = "61.0";
            }
            if (BROWSER_PLATFORM == null) {
                BROWSER_PLATFORM = "Windows 10";
            }
            driver.set(createSauceLabsDriver(BROWSER_TYPE, BROWSER_VERSION, BROWSER_PLATFORM, testName.get(), categoryGroup.get()));
        }
        return driver.get();
    }

    public static void beforeConfiguration() {
        startTimestamp = currentTimeStamp();
        String pathDir = new File("").getAbsolutePath();
        /*String com4jDll = pathDir + "\\src\\test\\resources\\hpConnection\\com4j-amd64.dll"
        System.load(com4jDll);*/

        File file = new File(pathDir + "\\target\\test-screen");
        if (!file.exists()) {
            if (file.mkdir()) {
                try {
                    FileUtils.cleanDirectory(new File(pathDir + "\\target\\test-screen\\"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("test-screen directory is created");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
        new File(pathDir + "\\target\\TestLogReport.html").delete();
        if (System.getProperty("webdriver.ie.driver") == null) {
            System.setProperty("webdriver.ie.driver", "src\\resources\\drivers\\IEdriverServer.exe");
        }
        if (System.getProperty("webdriver.edge.driver") == null) {
            System.setProperty("webdriver.edge.driver", "src\\resources\\drivers\\MicrosoftWebDriver.exe");
        }
        if (System.getProperty("webdriver.chrome.driver") == null) {
            System.setProperty("webdriver.chrome.driver", "src\\resources\\drivers\\chromedriver.exe");
        }
        if (System.getProperty("webdriver.gecko.driver") == null) {
            System.setProperty("webdriver.gecko.driver", "src\\resources\\drivers\\geckodriver.exe");
        }
    }

    private static WebDriver createSauceLabsDriver(String browser, String version, String os, String testMethod, String tag) {

        if (userID == null) {
            userID = "a049473";
        }
        if (vcnPass == null) {
            vcnPass = "";
        }
        ProxyConfiguration proxyConfiguration = new ProxyConfiguration(proxyAddress, "8080", userID, vcnPass);
        System.setProperty("http.proxyUser", proxyConfiguration.getUser());
        System.setProperty("http.proxyPassword", proxyConfiguration.getPassword());
        System.setProperty("http.proxyHost", proxyConfiguration.getHost());
        System.setProperty("http.proxyPort", proxyConfiguration.getPort());
        System.setProperty("https.proxyHost", proxyConfiguration.getHost());
        System.setProperty("https.proxyPort", proxyConfiguration.getPort());

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
        capabilities.setCapability(CapabilityType.VERSION, version);
        capabilities.setCapability(CapabilityType.PLATFORM, os);
        capabilities.setCapability("build", "DEMO_web_Regression_Tests_Build");
        capabilities.setCapability("tags", tag);
        capabilities.setCapability("parentTunnel", "projectGroupIT");
        capabilities.setCapability("name", testMethod);
        capabilities.setCapability("idleTimeout", 120);
        // Launch remote browser and set it as the current thread
        try {
            driver.set(new RemoteWebDriver(new URL("https://" + username + ":" + accessKey + "@ondemand.saucelabs.com:443/wd/hub"), capabilities));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        String id = ((RemoteWebDriver) getDriver()).getSessionId().toString();
        sessionId.set(id);
        return driver.get();
    }

    public static synchronized WebDriver getDriver() {
        return driver.get();
    }

}
