/**
 * 
 */
package com.testauto.setuptestng.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

/**
 * @author apopa
 *
 */
public class TestListener implements ITestListener {
	private long startTime;
	private long endTime;

	// This belongs to ITestListener and will execute before starting of Test
	// set/batch
	public void onStart(ITestContext arg0) {
		startTime = System.currentTimeMillis();
		Reporter.log("Started executing Test " + arg0.getName(), true);

	}

	// This belongs to ITestListener and will execute, once the Test set/batch
	// is finished
	public void onFinish(ITestContext arg0) {
		endTime = System.currentTimeMillis() - startTime;
		Reporter.log("Completed executing test " + arg0.getName() + " and took: " + endTime + "ms", true);

	}

	// This belongs to ITestListener and will execute only when the test is pass
	public void onTestSuccess(ITestResult arg0) {

		// This is calling the printTestResults method
		printTestResults(arg0);

	}

	// This belongs to ITestListener and will execute only on the event of fail
	// test
	public void onTestFailure(ITestResult arg0) {

		// This is calling the printTestResults method

		printTestResults(arg0);

	}

	// This belongs to ITestListener and will execute before the main test start
	// (@Test)
	public void onTestStart(ITestResult arg0) {

		System.out.println("The execution of the main test starts now");

	}

	// This belongs to ITestListener and will execute only if any of the main
	// test(@Test) get skipped
	public void onTestSkipped(ITestResult arg0) {

		printTestResults(arg0);

	}

	// Not implemented
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {

	}

	// This is the method which will be executed in case of test pass or fail

	// This will provide the information on the test
	private void printTestResults(ITestResult result) {

		Reporter.log("Test Method resides in " + result.getTestClass().getName(), true);

		if (result.getParameters().length != 0) {

			String params = null;

			for (Object parameter : result.getParameters()) {

				params += parameter.toString() + ",";

			}

			Reporter.log("Test Method had the following parameters : " + params, true);

		}

		String status = null;

		switch (result.getStatus()) {

		case ITestResult.SUCCESS:

			status = "Pass";

			break;

		case ITestResult.FAILURE:

			status = "Failed";

			break;

		case ITestResult.SKIP:

			status = "Skipped";

		}

		Reporter.log("Test Status: " + status, true);

	}

}
