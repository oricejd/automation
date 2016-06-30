package com.testauto.setuptestng.listeners;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;

public class InvokedMethodListener implements IInvokedMethodListener{
	private long startTime;
	private long endTime;

	@Override
	public void afterInvocation(IInvokedMethod arg0, ITestResult arg1) {
		endTime = System.currentTimeMillis() - startTime;
		String textMsg = "Completed executing following method : " + returnMethodName(arg0.getTestMethod()) + "and took: " + endTime + "ms";
		Reporter.log(textMsg, true);
		
	}

	@Override
	public void beforeInvocation(IInvokedMethod arg0, ITestResult arg1) {
		startTime = System.currentTimeMillis();
		String textMsg = "Started executing following method : " + returnMethodName(arg0.getTestMethod());
		Reporter.log(textMsg, true);
		
	}
	
	private String returnMethodName(ITestNGMethod method) {
		 
		return method.getRealClass().getSimpleName() + "." + method.getMethodName();
 
	}

}
