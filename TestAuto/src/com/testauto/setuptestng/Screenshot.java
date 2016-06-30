package com.testauto.setuptestng;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.log4j.Logger;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import com.testauto.seleniumsetup.WebDriverExtended;


public class Screenshot implements MethodRule {

	public static final String DEFAULT_SCREENSHOT_DIRECTORY = "target/surefire-reports/";
	WebDriverExtended driver;
	
	public static Logger LOG = Logger.getLogger("testLogger");
	
    public Screenshot() {
	}

	public void setDriver(WebDriverExtended driver) {
		this.driver = driver;
	}

	public Statement apply(final Statement statement, final FrameworkMethod frameworkMethod, final Object o) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    statement.evaluate();
                } catch (Throwable t) {
                    captureScreenshot(frameworkMethod.getName());
                    log();
                    throw t;
                }
            }

            public void log(){
            	LOG.info(frameworkMethod.getName());
            }
            
            public void captureScreenshot(String fileName) {
                try {
                    new File(DEFAULT_SCREENSHOT_DIRECTORY).mkdirs(); // Make sure the directory is there
                    FileOutputStream out = new FileOutputStream(DEFAULT_SCREENSHOT_DIRECTORY + "screenshot-" + fileName + ".png");
                    out.write(driver.getScreenshot());
                    out.close();
                } catch (Exception e) {
                	LOG.error("Could not take the screenshot! Cause: " + e.getClass().getName());
                    e.printStackTrace();
                }
            }
        };
    }
}
