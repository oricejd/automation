/**
 * 
 */
package com.testauto.setuptestng.listeners;

import org.testng.ISuite;
import org.testng.Reporter;

public class SuiteListener implements org.testng.ISuiteListener {

	private long startTime;
	private long endTime;

	// This belongs to ISuiteListener and will execute before the Suite start

	@Override
	public void onStart(ISuite arg0) {
		startTime = System.currentTimeMillis();
		Reporter.log("Started executing Suite " + arg0.getName(), true);

	}

	// This belongs to ISuiteListener and will execute, once the Suite is
	// finished

	@Override
	public void onFinish(ISuite arg0) {
		endTime = System.currentTimeMillis() - startTime;
		Reporter.log("Finished executing Suite " + arg0.getName() + " in " + endTime + " ms" , true);

	}

}
