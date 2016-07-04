package com.testauto.util;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.testauto.seleniumsetup.WebDriverExtended;

import static com.testauto.setuptestng.SeleniumTestCaseBase.LOG;;

public class CaptureScreenshotUtil {
	
	private static String fileSeperator = System.getProperty("file.separator");

	public static void captureScreenshot(WebDriverExtended driver, String childClassName) {
		String pathToScreenshot = "target/surefire-reports/";
		String fileName;
		try {
			// Make sure that the directory is there
			new File(pathToScreenshot).mkdirs();
			fileName = pathToScreenshot+"screenshot-" + childClassName + "_" + System.currentTimeMillis() + ".png";
			FileOutputStream out = new FileOutputStream(fileName);
			out.write(driver.getScreenshot());
			out.close();
			System.out.println("Screenshot can be found : " + fileName);
		} catch (Exception e) {
			LOG.info("Could not take the screenshot");
			e.printStackTrace();
		}
	}
	
	public static String takeScreenShot(WebDriver driver,
			String screenShotName, String testName) {
		try {
			File file = new File("Screenshots" + fileSeperator + "Results");
			if (!file.exists()) {
				System.out.println("File created " + file);
				file.mkdir();
			}

			File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File targetFile = new File("Screenshots" + fileSeperator + "Results" + fileSeperator + testName, screenShotName);
			FileUtils.copyFile(screenshotFile, targetFile);

			return screenShotName;
		} catch (Exception e) {
			System.out.println("An exception occured while taking screenshot " + e.getCause());
			return null;
		}
	}
	
	public static String getTestClassName(String testName) {
		String[] reqTestClassname = testName.split("\\.");
		int i = reqTestClassname.length - 1;
		System.out.println("Required Test Name : " + reqTestClassname[i]);
		return reqTestClassname[i];
	}
}
