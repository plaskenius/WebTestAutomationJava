package com.project.project.components;

import org.apache.log4j.Logger;

import static com.project.project.components.Utils.readEnv;


/**
 * This class contains common methods used for logging test execution using log4j logger
 * @author PawelPie
 *
 */
public class TestLog {

    static Logger log = Logger.getLogger(TestLog.class.getName());

    public void logStartTestExecution(String testCaseName){
        log.info(">>>>>>>>>>>>>>>>>>>>>");
        log.info("Test "+testCaseName+" started");
        log.info(">>>>>>>>>>>>>>>>>>>>>");
    }

    public void logEndTestExecution(String testCaseName, int status){
        switch (status) {
            case 1:
                log.info("Test "+testCaseName+" finished --> PASSED");
                break;
            case 2:
                String pathDir = new java.io.File("").getAbsolutePath();
                String pathFile = pathDir + "\\target\\test-screen\\";
                log.info("Screenshot has been taken and saved in "+ pathFile);
                log.error("Test "+testCaseName+" finished --> FAILED");
                break;
            case 3:
                log.info("Test "+testCaseName+" finished --> SKIPPED");
                break;
            default:
                log.info("Test "+testCaseName+" finished --> RESULT UNKNOWN");
                break;
        }
        log.info("Test environment: "+readEnv());
        //log.info("Browser: "+driver.getCapabilities().getBrowserName()+" ver."+driver.getCapabilities().getVersion())
        //log.info("OS:"+driver.capabilities["platform"])
        //log.info("Url in the End of Test: "+driver.getCurrentUrl())
        log.info("<<<<<<<<<<<<<<<<<<<<<");
    }

    public void logStartTestClassExecution(String testClassName){
        log.info("-------------------------------------------------------");
        log.info("Execution of tests from "+testClassName+" class started");
        log.info("-------------------------------------------------------");
    }

    public void logEndTestClassExecution(String testClassName){
        log.info("-------------------------------------------------------");
        log.info("Execution of tests from "+testClassName+" class finished");
        log.info("-------------------------------------------------------");
    }

    public void info(String message) {
        log.info(message);
    }

    public void warn(String message) {
        log.warn(message);
    }

    public void error(String message) {
        log.error(message);
    }

    public void fatal(String message) {
        log.fatal(message);
    }

    public void debug(String message) {
        log.debug(message);
    }
}
