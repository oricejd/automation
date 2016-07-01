/**
 * 
 */
package com.testauto.setuptestng.listeners;

/**
 * @author apopa
 *
 */

import org.testng.IExecutionListener;
import org.testng.Reporter;

public class ExecutionListener implements IExecutionListener{
	
    private long startTime;

    @Override
    public void onExecutionStart() {
        startTime = System.currentTimeMillis();
        System.out.println("TestNG is going to start");    
		Reporter.log("About to begin executing TestNG RUN" , true);

    }

    @Override
    public void onExecutionFinish() {
        System.out.println("TestNG has finished, took around " + (System.currentTimeMillis() - startTime) + "ms");
        Reporter.log("Finished executing TestNG RUN" , true);
    }


}
